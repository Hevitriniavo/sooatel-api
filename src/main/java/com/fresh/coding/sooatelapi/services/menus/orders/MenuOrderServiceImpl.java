package com.fresh.coding.sooatelapi.services.menus.orders;

import com.fresh.coding.sooatelapi.dtos.customers.CustomerDTO;
import com.fresh.coding.sooatelapi.dtos.menu.orders.CreateMenuOrderDTO;
import com.fresh.coding.sooatelapi.dtos.menu.orders.MenuOrderDTO;
import com.fresh.coding.sooatelapi.dtos.menu.orders.MenuOrderSummarized;
import com.fresh.coding.sooatelapi.dtos.menus.MenuSummarized;
import com.fresh.coding.sooatelapi.dtos.menus.UpdateOrderStatusDTO;
import com.fresh.coding.sooatelapi.dtos.rooms.RoomDTO;
import com.fresh.coding.sooatelapi.dtos.tables.TableSummarized;
import com.fresh.coding.sooatelapi.entities.*;
import com.fresh.coding.sooatelapi.enums.OperationType;
import com.fresh.coding.sooatelapi.enums.OrderStatus;
import com.fresh.coding.sooatelapi.exceptions.HttpNotFoundException;
import com.fresh.coding.sooatelapi.repositories.OperationRepository;
import com.fresh.coding.sooatelapi.repositories.RepositoryFactory;
import com.fresh.coding.sooatelapi.repositories.SessionOccupationRepository;
import com.fresh.coding.sooatelapi.repositories.StockRepository;
import com.fresh.coding.sooatelapi.services.invoices.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MenuOrderServiceImpl implements MenuOrderService {
    private final RepositoryFactory repositoryFactory;
    private final InvoiceService invoiceService;

    @Override
    @Transactional
    public MenuOrderDTO createMenuOrder(CreateMenuOrderDTO dto) {
        Long roomNumber = dto.getRoomNumber();
        Long tableNumber = dto.getTableNumber();

        if (roomNumber == null && tableNumber == null)
            throw new IllegalArgumentException("Vous devez fournir soit un numéro de salle, soit un numéro de table.");
        if (roomNumber != null && tableNumber != null)
            throw new IllegalArgumentException("Vous ne pouvez pas fournir à la fois un numéro de salle et un numéro de table.");

        LocalDateTime now = LocalDateTime.now();

        var menuRepo = repositoryFactory.getMenuRepository();
        var roomRepo = repositoryFactory.getRoomRepository();
        var tableRepo = repositoryFactory.getTableRepository();
        var customerRepo = repositoryFactory.getCustomerRepository();
        var stockRepo = repositoryFactory.getStockRepository();
        var operationRepo = repositoryFactory.getOperationRepository();
        var orderRepo = repositoryFactory.getMenuOrderRepository();
        var orderLineRepo = repositoryFactory.getOrderLineRepository();
        var sessionRepo = repositoryFactory.getSessionOccupationRepository();

        Customer customer = null;
        if (dto.getCustomerId() != null) {
            customer = customerRepo.findById(dto.getCustomerId())
                    .orElseThrow(() -> new HttpNotFoundException("Aucun client trouvé avec l'ID : " + dto.getCustomerId()));
        }

        Room room = null;
        TableEntity table = null;
        if (roomNumber != null) {
            room = roomRepo.findByRoomNumber(roomNumber)
                    .orElseThrow(() -> new HttpNotFoundException("Aucune salle trouvée avec le numéro " + roomNumber));
        } else {
            table = tableRepo.findByTableNumber(tableNumber)
                    .orElseThrow(() -> new HttpNotFoundException("Aucune table trouvée avec le numéro " + tableNumber));
        }

        SessionOccupation session = getOrCreateActiveSession(sessionRepo, customer, room, table, now);

        List<String> missingIngredients = new ArrayList<>();
        for (var item : dto.getMenuItems()) {
            Menu menu = menuRepo.findById(item.getMenuId())
                    .orElseThrow(() -> new HttpNotFoundException("Menu introuvable avec l'ID : " + item.getMenuId()));
            List<String> shortages = checkStock(menu, item.getQuantity());
            if (!shortages.isEmpty()) missingIngredients.addAll(shortages);
        }
        if (!missingIngredients.isEmpty())
            throw new IllegalArgumentException("Ingrédients manquants : " + String.join(", ", missingIngredients));

        Order order = Order.builder()
                .customer(customer)
                .room(room)
                .table(table)
                .orderDate(now)
                .orderStatus(OrderStatus.NOT_DELIVERED)
                .sessionOccupation(session)
                .build();
        order = orderRepo.save(order);

        List<MenuOrderDTO.MenuItemSummarizedDTO> summarizedItems = new ArrayList<>();

        for (var item : dto.getMenuItems()) {
            Menu menu = menuRepo.findById(item.getMenuId())
                    .orElseThrow(() -> new HttpNotFoundException("Menu introuvable avec l'ID : " + item.getMenuId()));

            var totalPrice = menu.getPrice() * item.getQuantity();

            OrderLine orderLine = OrderLine.builder()
                    .order(order)
                    .menu(menu)
                    .quantity(item.getQuantity())
                    .unitPrice(menu.getPrice())
                    .totalPrice(totalPrice)
                    .build();
            orderLineRepo.save(orderLine);
            order.getOrderLines().add(orderLine);

            updateStockAndOperations(menu, item.getQuantity(), stockRepo, operationRepo, now, order.getId());

            summarizedItems.add(new MenuOrderDTO.MenuItemSummarizedDTO(
                    menu.getId(),
                    item.getQuantity(),
                    totalPrice,
                    OrderStatus.NOT_DELIVERED
            ));
        }

        return MenuOrderDTO.builder()
                .customerId(customer != null ? customer.getId() : null)
                .roomId(room != null ? room.getId() : null)
                .roomNumber(room != null ? room.getNumber() : null)
                .tableId(table != null ? table.getId() : null)
                .tableNumber(table != null ? table.getNumber() : null)
                .orderDate(order.getOrderDate())
                .menuItems(summarizedItems)
                .build();
    }

    private SessionOccupation getOrCreateActiveSession(
            SessionOccupationRepository sessionRepo,
            Customer customer,
            Room room,
            TableEntity table,
            LocalDateTime now) {
        Optional<SessionOccupation> sessionOpt = Optional.empty();
        if (table != null) {
            sessionOpt = sessionRepo.findByTableIdAndEndedAtIsNull(table.getId());
        } else if (room != null) {
            sessionOpt = sessionRepo.findByRoomIdAndEndedAtIsNull(room.getId());
        }

        return sessionOpt.orElseGet(() -> {
            SessionOccupation session = SessionOccupation.builder()
                    .customer(customer)
                    .room(room)
                    .table(table)
                    .startedAt(now)
                    .description("Session démarrée automatiquement à la commande.")
                    .build();
            return sessionRepo.save(session);
        });
    }

    private void updateStockAndOperations(
            Menu menu,
            double quantity,
            StockRepository stockRepo,
            OperationRepository operationRepo,
            LocalDateTime now,
            Long orderId) {
        for (var ing : menu.getMenuIngredients()) {
            var stock = ing.getIngredient().getStock();
            if (stock != null) {
                double requiredQty = ing.getQuantity() * quantity;
                stock.setQuantity(stock.getQuantity() - requiredQty);
                stockRepo.save(stock);

                var operation = Operation.builder()
                        .stock(stock)
                        .type(OperationType.SORTIE)
                        .date(now)
                        .quantity(requiredQty)
                        .description("Sortie de stock liée à la commande #" + orderId)
                        .build();
                operationRepo.save(operation);
            }
        }
    }

    @Override
    @Transactional
    public void updateOrderStatus(UpdateOrderStatusDTO orderStatusDTO) {
        var orderRepo = repositoryFactory.getMenuOrderRepository();
        for (Long orderId : orderStatusDTO.getOrderIds()) {
            Order order = orderRepo.findById(orderId)
                    .orElseThrow(() -> new HttpNotFoundException("Aucune commande trouvée avec l'ID " + orderId));
            order.setOrderStatus(orderStatusDTO.getOrderStatus());
            orderRepo.save(order);
        }
    }

    @Override
    @Transactional
    public void updateOrderStatus(Long orderId, OrderStatus newStatus) {
        var orderRepository = repositoryFactory.getMenuOrderRepository();
        var order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

        var previousStatus = order.getOrderStatus();
        order.setOrderStatus(newStatus);
        orderRepository.save(order);

        if (!OrderStatus.DELIVERED.equals(previousStatus) && OrderStatus.DELIVERED.equals(newStatus)) {
            invoiceService.generateInvoiceIfOrderDelivered(order.getId());
        }
    }

    @Override
    @Transactional
    public void deleteOrderById(Long orderId) {
        var orderRepo = repositoryFactory.getMenuOrderRepository();
        var roomRepo = repositoryFactory.getRoomRepository();
        var tableRepo = repositoryFactory.getTableRepository();

        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new HttpNotFoundException("Aucune commande trouvée avec l'ID " + orderId));

        if (order.getRoom() != null) {
            roomRepo.save(order.getRoom());
        }
        if (order.getTable() != null) {
            tableRepo.save(order.getTable());
        }

        orderRepo.delete(order);
    }

    @Override
    public List<Map<String, Object>> groupByTableOrRoom(Long tableNumber, Long roomNumber) {
        List<Order> orders;
        var menuOrderRepository = repositoryFactory.getMenuOrderRepository();

        if (tableNumber != null && roomNumber != null) {
            orders = menuOrderRepository.findAllByRoomNumberOrTableNumber(roomNumber, tableNumber);
        } else if (roomNumber != null) {
            orders = menuOrderRepository.findAllByRoomNumber(roomNumber);
        } else if (tableNumber != null) {
            orders = menuOrderRepository.findAllByTableNumber(tableNumber);
        } else {
            orders = menuOrderRepository.findAll();
        }
        return groupOrders(orders);
    }

    private List<Map<String, Object>> groupOrders(List<Order> orders) {
        List<Map<String, Object>> res = new ArrayList<>();
        for (var order : orders) {
            Map<String, Object> orderMap = new HashMap<>();
            orderMap.put("id", order.getId());
            orderMap.put("orderDate", order.getOrderDate());
            orderMap.put("status", order.getOrderStatus());
            orderMap.put("orderLines", order.getOrderLines());
            orderMap.put("sessionOccupation", order.getSessionOccupation());
            if (order.getRoom() != null) {
                orderMap.put("room", order.getRoom());
            }
            if (order.getTable() != null) {
                orderMap.put("table", order.getTable());
            }
            res.add(orderMap);
        }

        return res;
    }

    @Override
    public List<MenuOrderSummarized> findAllOrdersByTable(Long tableNumber) {
        var tableRepo = repositoryFactory.getTableRepository();
        var orderRepo = repositoryFactory.getMenuOrderRepository();

        TableEntity table = tableRepo.findByTableNumber(tableNumber)
                .orElseThrow(() -> new HttpNotFoundException("Table non trouvée avec le numéro " + tableNumber));

        List<Order> orders = orderRepo.findAllByTableId(table.getId());

        return mapOrdersToMenuOrderSummarized(orders);
    }

    @Override
    public List<MenuOrderSummarized> findAllOrdersByRoom(Long roomNumber) {
        var roomRepo = repositoryFactory.getRoomRepository();
        var orderRepo = repositoryFactory.getMenuOrderRepository();

        Room room = roomRepo.findByRoomNumber(roomNumber)
                .orElseThrow(() -> new HttpNotFoundException("Salle non trouvée avec le numéro " + roomNumber));

        List<Order> orders = orderRepo.findAllByRoomId(room.getId());

        return mapOrdersToMenuOrderSummarized(orders);
    }

    private List<MenuOrderSummarized> mapOrdersToMenuOrderSummarized(List<Order> orders) {
        List<MenuOrderSummarized> result = new ArrayList<>();
        for (Order order : orders) {
            for (OrderLine orderLine : order.getOrderLines()) {
                MenuOrderSummarized dto = new MenuOrderSummarized();
                dto.setId(order.getId());

                if (order.getCustomer() != null)
                    dto.setCustomer(new CustomerDTO(order.getCustomer()));

                if (order.getRoom() != null)
                    dto.setRoom(new RoomDTO(order.getRoom()));

                if (order.getTable() != null)
                    dto.setTable(new TableSummarized(
                            order.getTable().getId(),
                            order.getTable().getNumber(),
                            order.getTable().getCapacity(),
                            order.getTable().getCreatedAt(),
                            order.getTable().getUpdatedAt()
                    ));

                dto.setOrderDate(order.getOrderDate());
                dto.setQuantity(orderLine.getQuantity().doubleValue());
                dto.setCost(orderLine.getTotalPrice().doubleValue());
                dto.setOrderStatus(order.getOrderStatus());

                if (orderLine.getMenu() != null) {
                    var menu = orderLine.getMenu();
                    dto.setMenu(new MenuSummarized(
                            menu.getId(),
                            menu.getName(),
                            menu.getDescription(),
                            menu.getPrice(),
                            menu.getMenuGroup() != null ? menu.getMenuGroup().getId() : null,
                            menu.getCreatedAt(),
                            menu.getUpdatedAt(),
                            menu.getStatus()
                    ));
                }

                result.add(dto);
            }
        }
        return result;
    }

    private List<String> checkStock(Menu menu, double quantityOrdered) {
        List<String> shortages = new ArrayList<>();
        for (var menuIngredient : menu.getMenuIngredients()) {
            var stock = menuIngredient.getIngredient().getStock();
            if (stock != null) {
                double availableQuantity = stock.getQuantity();
                double requiredQuantity = menuIngredient.getQuantity() * quantityOrdered;
                if (availableQuantity < requiredQuantity)
                    shortages.add(menuIngredient.getIngredient().getName());
            }
        }
        return shortages;
    }
}

package com.fresh.coding.sooatelapi.services.menus.orders;

import com.fresh.coding.sooatelapi.dtos.customers.CustomerDTO;
import com.fresh.coding.sooatelapi.dtos.menu.orders.CreateMenuOrderDTO;
import com.fresh.coding.sooatelapi.dtos.menu.orders.MenuOrderDTO;
import com.fresh.coding.sooatelapi.dtos.menu.orders.MenuOrderSummarized;
import com.fresh.coding.sooatelapi.dtos.menus.MenuSummarized;
import com.fresh.coding.sooatelapi.dtos.payments.PaymentSummarized;
import com.fresh.coding.sooatelapi.dtos.rooms.RoomDTO;
import com.fresh.coding.sooatelapi.dtos.tables.TableSummarized;
import com.fresh.coding.sooatelapi.entities.*;
import com.fresh.coding.sooatelapi.enums.OperationType;
import com.fresh.coding.sooatelapi.enums.OrderStatus;
import com.fresh.coding.sooatelapi.enums.RoomStatus;
import com.fresh.coding.sooatelapi.enums.TableStatus;
import com.fresh.coding.sooatelapi.exceptions.HttpNotFoundException;
import com.fresh.coding.sooatelapi.repositories.RepositoryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuOrderServiceImpl implements MenuOrderService {

    private final RepositoryFactory repositoryFactory;


    @Override
    @Transactional
    public MenuOrderDTO createMenuOrder(CreateMenuOrderDTO createMenuOrderDTO) {
        var roomNumber = createMenuOrderDTO.getRoomNumber();
        var tableNumber = createMenuOrderDTO.getTableNumber();
        var menuOrderRepository = repositoryFactory.getMenuOrderRepository();


        if (roomNumber == null && tableNumber == null) {
            throw new IllegalArgumentException("Either roomNumber or tableNumber must be provided.");
        }

        if (roomNumber != null && tableNumber != null) {
            throw new IllegalArgumentException("Cannot have both roomNumber and tableNumber at the same time.");
        }


        var now = LocalDateTime.now();
        var menuRepository = repositoryFactory.getMenuRepository();
        var roomRepository = repositoryFactory.getRoomRepository();
        var tableRepository = repositoryFactory.getTableRepository();
        var customerRepository = repositoryFactory.getCustomerRepository();
        var stockRepository = repositoryFactory.getStockRepository();
        var operationRepository = repositoryFactory.getOperationRepository();

        var customer = createMenuOrderDTO.getCustomerId() != null
                ? customerRepository.findById(createMenuOrderDTO.getCustomerId())
                .orElseThrow(() -> new HttpNotFoundException("Customer not found"))
                : null;

        Room room = null;
        RestTable table = null;


        if (roomNumber != null) {
            room = roomRepository.findByRoomNumber(roomNumber)
                    .orElseThrow(() -> new HttpNotFoundException("Room not found"));
            room.setStatus(RoomStatus.NOT_AVAILABLE);
        }

        if (tableNumber != null) {
            table = tableRepository.findByNumber(tableNumber)
                    .orElseThrow(() -> new HttpNotFoundException("Table not found"));
            table.setStatus(TableStatus.NOT_AVAILABLE);
        }


        List<MenuOrder> savedMenuOrders = new ArrayList<>();
        List<String> missingIngredients = new ArrayList<>();

        for (var item : createMenuOrderDTO.getMenuItems()) {
            var menu = menuRepository.findById(item.getMenuId())
                    .orElseThrow(() -> new HttpNotFoundException("Menu not found"));

            var ingredientShortages = checkStock(menu, item.getQuantity());
            if (!ingredientShortages.isEmpty()) {
                missingIngredients.addAll(ingredientShortages);
            }
        }

        if (!missingIngredients.isEmpty()) {
            throw new IllegalArgumentException( String.join(", ", missingIngredients));
        }

        for (var item : createMenuOrderDTO.getMenuItems()) {
            var menu = menuRepository.findById(item.getMenuId())
                    .orElseThrow(() -> new HttpNotFoundException("Menu not found"));

            var menuOrder = MenuOrder.builder()
                    .customer(customer)
                    .orderDate(now)
                    .menu(menu)
                    .quantity(item.getQuantity())
                    .cost(item.getQuantity() * menu.getPrice())
                    .orderStatus(OrderStatus.NOT_DELIVERED)
                    .build();

            if (room != null) {
                menuOrder.setRoom(room);
            }
            if (table != null) {
                menuOrder.setTable(table);
            }

            menuOrderRepository.save(menuOrder);
            savedMenuOrders.add(menuOrder);

            for (var menuIngredient : menu.getMenuIngredients()) {
                var stock = menuIngredient.getIngredient().getStock();
                if (stock != null) {
                    double requiredQuantity = menuIngredient.getQuantity() * item.getQuantity();
                    double newQuantity = stock.getQuantity() - requiredQuantity;
                    stock.setQuantity(newQuantity);

                    stockRepository.save(stock);

                    var operation = Operation.builder()
                            .stock(stock)
                            .type(OperationType.SORTIE)
                            .date(now)
                            .quantity(requiredQuantity)
                            .description("Mise à jour du stock suite à la commande #" + menuOrder.getId())
                            .build();
                    operationRepository.save(operation);
                }
            }
        }

        return MenuOrderDTO.builder()
                .customerId(createMenuOrderDTO.getCustomerId())
                .orderDate(now)
                .roomId(room != null ? room.getId(): null)
                .tableId(table != null ? table.getId(): null)
                .tableNumber(tableNumber)
                .roomNumber(roomNumber)
                .menuItems(savedMenuOrders.stream()
                        .map(order -> new MenuOrderDTO.MenuItemSummarizedDTO(
                                order.getId(),
                                order.getQuantity(),
                                order.getCost(),
                                order.getOrderStatus()
                        ))
                        .collect(Collectors.toList()))
                .build();
    }


    @Override
    @Transactional
    public void updateOrderStatus(Long orderId, OrderStatus newStatus) {
        var menuOrderRepository = repositoryFactory.getMenuOrderRepository();
        MenuOrder menuOrder = menuOrderRepository.findById(orderId)
                .orElseThrow(() -> new HttpNotFoundException("Order not found"));
        menuOrder.setOrderStatus(newStatus);
        menuOrderRepository.save(menuOrder);
    }

    @Override
    @Transactional
    public void deleteOrderById(Long orderId) {
        var menuOrderRepository = repositoryFactory.getMenuOrderRepository();
        var menuOrder = menuOrderRepository.findById(orderId)
                .orElseThrow(() -> new HttpNotFoundException("Order not found"));

        if (menuOrder.getRoom() != null) {
            var room = menuOrder.getRoom();
            room.setStatus(RoomStatus.AVAILABLE);
            repositoryFactory.getRoomRepository().save(room);
        }

        if (menuOrder.getTable() != null) {
            var table = menuOrder.getTable();
            table.setStatus(TableStatus.AVAILABLE);
            repositoryFactory.getTableRepository().save(table);
        }

        menuOrderRepository.delete(menuOrder);
    }

    @Override
    public List<Map<String, Object>> groupByTableOrRoom(Integer tableNumber, Integer roomNumber) {
        List<MenuOrder> orders;
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

        return orders.stream()
                .collect(Collectors.groupingBy(order -> {
                    Map<String, Object> map = new HashMap<>();
                    if (order.getRoom() != null) {
                        map.put("type", "room");
                        map.put("number", order.getRoom().getRoomNumber());
                    } else if (order.getTable() != null) {
                        map.put("type", "table");
                        map.put("number", order.getTable().getNumber());
                    }
                    map.put("orderStatus", order.getOrderStatus());
                    map.put("payment", order.getPayment() != null ? toPayment(order.getPayment()) : null);
                    return map;
                }))
                .entrySet()
                .stream()
                .map(entry -> {
                    Map<String, Object> key = entry.getKey();
                    List<MenuOrder> groupedOrders = entry.getValue();

                    List<String> menus = groupedOrders.stream()
                            .map(order -> order.getMenu().getName())
                            .distinct()
                            .collect(Collectors.toList());

                    key.put("menus", menus);
                    return key;
                })
                .collect(Collectors.toList());
    }

    private PaymentSummarized toPayment(Payment payment) {
        return new PaymentSummarized(
                payment.getId(),
                payment.getReservation() != null ? payment.getReservation().getId() : null,
                payment.getPaymentDate(),
                payment.getUpdatedAt(),
                payment.getAmount(),
                payment.getPaymentMethod(),
                payment.getStatus(),
                payment.getDescription()
        );
    }

    @Override
    public List<MenuOrderSummarized> findAllOrdersByTable(Integer tableNumber) {
        var menuOrderRepository = repositoryFactory.getMenuOrderRepository();
        List<MenuOrder> orders = menuOrderRepository.findAllByTableNumber(tableNumber);


        return orders.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MenuOrderSummarized> findAllOrdersByRoom(Integer roomNumber) {
        var menuOrderRepository = repositoryFactory.getMenuOrderRepository();
        List<MenuOrder> orders = menuOrderRepository.findAllByRoomNumber(roomNumber);

        return orders.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }


    private List<String> checkStock(Menu menu, double quantityOrdered) {
        List<String> shortages = new ArrayList<>();

        for (var menuIngredient : menu.getMenuIngredients()) {
            var stock = menuIngredient.getIngredient().getStock();
            if (stock != null) {
                double availableQuantity = stock.getQuantity();
                double requiredQuantity = menuIngredient.getQuantity() * quantityOrdered;

                if (availableQuantity < requiredQuantity) {
                    shortages.add(menuIngredient.getIngredient().getName());
                }
            }
        }

        return shortages;
    }


    private MenuOrderSummarized mapToDTO(MenuOrder menuOrder) {
        MenuOrderSummarized dto = new MenuOrderSummarized();

        BeanUtils.copyProperties(menuOrder, dto, "customer", "room", "table", "menu");

        if (menuOrder.getCustomer() != null) {
            var customerDTO = new CustomerDTO();
            BeanUtils.copyProperties(menuOrder.getCustomer(), customerDTO);
            dto.setCustomer(customerDTO);
        }

        if (menuOrder.getRoom() != null) {
            RoomDTO roomDTO = new RoomDTO();
            BeanUtils.copyProperties(menuOrder.getRoom(), roomDTO);
            dto.setRoom(roomDTO);
        }

        if (menuOrder.getPayment() != null) {
            dto.setPaymentId(dto.getPaymentId());
        }

        if (menuOrder.getMenu() != null) {
            Menu menu = menuOrder.getMenu();

            var menuDTO = new MenuSummarized(
                    menu.getId(),
                    menu.getName(),
                    menu.getDescription(),
                    menu.getPrice(),
                    menu.getCategory().getId(),
                    menu.getCreatedAt(),
                    menu.getUpdatedAt(),
                    menu.getStatus()
            );

            dto.setMenu(menuDTO);
        }


        if (menuOrder.getTable() != null) {
            var tableDTO = new TableSummarized(
                    menuOrder.getTable().getId(),
                    menuOrder.getTable().getNumber(),
                    menuOrder.getTable().getCapacity(),
                    menuOrder.getTable().getStatus(),
                    menuOrder.getTable().getCreatedAt(),
                    menuOrder.getTable().getUpdatedAt()
            );
            dto.setTable(tableDTO);
        }

        return dto;
    }

}

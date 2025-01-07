package com.fresh.coding.sooatelapi.services.menus.orders;

import com.fresh.coding.sooatelapi.dtos.menu.orders.CreateMenuOrderDTO;
import com.fresh.coding.sooatelapi.dtos.menu.orders.MenuOrderDTO;
import com.fresh.coding.sooatelapi.entities.*;
import com.fresh.coding.sooatelapi.enums.OperationType;
import com.fresh.coding.sooatelapi.enums.OrderStatus;
import com.fresh.coding.sooatelapi.enums.RoomStatus;
import com.fresh.coding.sooatelapi.enums.TableStatus;
import com.fresh.coding.sooatelapi.exceptions.HttpBadRequestException;
import com.fresh.coding.sooatelapi.exceptions.HttpNotFoundException;
import com.fresh.coding.sooatelapi.repositories.RepositoryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
        
        menuOrderRepository.findByTableNumber(tableNumber)
                .ifPresent(order -> {
                    throw new HttpBadRequestException("Une commande existe déjà pour la table numéro : " + tableNumber);
                });

        menuOrderRepository.findByRoomRoomNumber(roomNumber)
                .ifPresent(order -> {
                    throw new HttpBadRequestException("Une commande existe déjà pour la chambre numéro : " + roomNumber);
                });

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
        if (tableNumber != null){
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
}

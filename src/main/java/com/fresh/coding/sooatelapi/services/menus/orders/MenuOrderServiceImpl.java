package com.fresh.coding.sooatelapi.services.menus.orders;

import com.fresh.coding.sooatelapi.dtos.menu.orders.CreateMenuOrderDTO;
import com.fresh.coding.sooatelapi.dtos.menu.orders.MenuOrderDTO;
import com.fresh.coding.sooatelapi.entities.*;
import com.fresh.coding.sooatelapi.enums.OperationType;
import com.fresh.coding.sooatelapi.enums.OrderStatus;
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
        var roomId = createMenuOrderDTO.getRoomId();
        var tableId = createMenuOrderDTO.getTableId();

        if (roomId == null && tableId == null) {
            throw new IllegalArgumentException("Either roomId or tableId must be provided.");
        }

        if (roomId != null && tableId != null) {
            throw new IllegalArgumentException("Cannot have both roomId and tableId at the same time.");
        }

        var now = LocalDateTime.now();
        var menuRepository = repositoryFactory.getMenuRepository();
        var roomRepository = repositoryFactory.getRoomRepository();
        var tableRepository = repositoryFactory.getTableRepository();
        var customerRepository = repositoryFactory.getCustomerRepository();
        var menuOrderRepository = repositoryFactory.getMenuOrderRepository();
        var stockRepository = repositoryFactory.getStockRepository();
        var operationRepository = repositoryFactory.getOperationRepository();

        var customer = createMenuOrderDTO.getCustomerId() != null
                ? customerRepository.findById(createMenuOrderDTO.getCustomerId())
                .orElseThrow(() -> new HttpNotFoundException("Customer not found"))
                : null;

        Room room = null;
        RestTable table = null;

        if (roomId != null) {
            room = roomRepository.findById(roomId)
                    .orElseThrow(() -> new HttpNotFoundException("Room not found"));
        } else {
            table = tableRepository.findById(tableId)
                    .orElseThrow(() -> new HttpNotFoundException("Table not found"));
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
            throw new IllegalArgumentException("Missing ingredients: " + String.join(", ", missingIngredients));
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
                    .orderStatus(OrderStatus.PENDING)
                    .build();

            if (room != null) {
                menuOrder.setRoom(room);
            } else if (table != null) {
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
                            .quantity(item.getQuantity())
                            .description("Mise à jour du stock suite à la commande #" + menuOrder.getId())
                            .build();
                    operationRepository.save(operation);
                }
            }
        }

        return MenuOrderDTO.builder()
                .customerId(createMenuOrderDTO.getCustomerId())
                .orderDate(now)
                .roomId(roomId)
                .tableId(tableId)
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
    public void deleteMenuIngredientById(Long menuIngredientId) {
        var menuIngredientRepository = repositoryFactory.getMenuIngredientRepository();

        if (!menuIngredientRepository.existsById(menuIngredientId)) {
            throw new HttpNotFoundException("MenuIngredient not found with ID: " + menuIngredientId);
        }

        menuIngredientRepository.deleteById(menuIngredientId);
    }

    @Override
    @Transactional
    public void deleteMenuIngredientByMenuIdAndIngredientId(Long menuId, Long ingredientId) {
        var menuIngredientRepository = repositoryFactory.getMenuIngredientRepository();
        MenuIngredient menuIngredient = menuIngredientRepository.findByMenuIdAndIngredientId(menuId, ingredientId)
                .orElseThrow(() -> new HttpNotFoundException("Menu ingredient not found"));
        menuIngredientRepository.delete(menuIngredient);
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

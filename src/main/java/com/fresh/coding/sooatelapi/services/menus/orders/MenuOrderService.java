package com.fresh.coding.sooatelapi.services.menus.orders;

import com.fresh.coding.sooatelapi.dtos.menu.orders.CreateMenuOrderDTO;
import com.fresh.coding.sooatelapi.dtos.menu.orders.MenuOrderDTO;
import com.fresh.coding.sooatelapi.enums.OrderStatus;

public interface MenuOrderService {
    MenuOrderDTO createMenuOrder(CreateMenuOrderDTO createMenuOrderDTO);
    void updateOrderStatus(Long orderId, OrderStatus newStatus);

    void deleteMenuIngredientById(Long menuIngredientId);

    void deleteMenuIngredientByMenuIdAndIngredientId(Long menuId, Long ingredientId);
}

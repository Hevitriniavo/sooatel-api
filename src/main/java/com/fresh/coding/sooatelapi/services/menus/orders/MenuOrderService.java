package com.fresh.coding.sooatelapi.services.menus.orders;

import com.fresh.coding.sooatelapi.dtos.menu.orders.CreateMenuOrderDTO;
import com.fresh.coding.sooatelapi.dtos.menu.orders.MenuOrderDTO;
import com.fresh.coding.sooatelapi.dtos.menu.orders.MenuOrderSummarized;
import com.fresh.coding.sooatelapi.enums.OrderStatus;

import java.util.List;
import java.util.Map;

public interface MenuOrderService {
    MenuOrderDTO createMenuOrder(CreateMenuOrderDTO createMenuOrderDTO);
    void updateOrderStatus(Long orderId, OrderStatus newStatus);

    void deleteOrderById(Long orderId);

    List<Map<String, Object>> groupByTableOrRoom(Integer tableNumber, Integer roomNumber);

    List<MenuOrderSummarized> findAllOrdersByTable(Integer tableNumber);

    List<MenuOrderSummarized> findAllOrdersByRoom(Integer roomNumber);

}

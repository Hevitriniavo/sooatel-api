package com.fresh.coding.sooatelapi.services.menus.orders;

import com.fresh.coding.sooatelapi.dtos.OrderDTO;
import com.fresh.coding.sooatelapi.dtos.menu.orders.CreateMenuOrderDTO;
import com.fresh.coding.sooatelapi.dtos.menu.orders.MenuOrderDTO;
import com.fresh.coding.sooatelapi.dtos.menu.orders.MenuOrderSummarized;
import com.fresh.coding.sooatelapi.dtos.menus.UpdateOrderStatusDTO;
import com.fresh.coding.sooatelapi.enums.OrderStatus;

import java.util.List;
import java.util.Map;

public interface MenuOrderService {
    MenuOrderDTO createMenuOrder(CreateMenuOrderDTO createMenuOrderDTO);
    void updateOrderStatus(UpdateOrderStatusDTO orderStatusDTO);
    void updateOrderStatus(Long orderId, OrderStatus newStatus);
    void deleteOrderById(Long orderId);
    void attachOrderLines(Long orderId, List<CreateMenuOrderDTO.MenuItemDTO> menuItems);
    List<MenuOrderDTO> getAllOrdersWithLines();
    List<OrderDTO> groupByTableOrRoom(Long tableNumber, Long roomNumber);

    List<MenuOrderSummarized> findAllOrdersByTable(Long tableNumber);

    List<MenuOrderSummarized> findAllOrdersByRoom(Long roomNumber);

}

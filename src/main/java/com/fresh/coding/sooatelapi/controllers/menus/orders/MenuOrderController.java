package com.fresh.coding.sooatelapi.controllers.menus.orders;

import com.fresh.coding.sooatelapi.dtos.OrderDTO;
import com.fresh.coding.sooatelapi.dtos.menu.orders.CreateMenuOrderDTO;
import com.fresh.coding.sooatelapi.dtos.menu.orders.MenuOrderDTO;
import com.fresh.coding.sooatelapi.dtos.menu.orders.MenuOrderSummarized;
import com.fresh.coding.sooatelapi.dtos.menus.UpdateOrderStatusDTO;
import com.fresh.coding.sooatelapi.enums.OrderStatus;
import com.fresh.coding.sooatelapi.services.menus.orders.MenuOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/menu-orders")
@RequiredArgsConstructor
public class MenuOrderController {
    private final MenuOrderService menuOrderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MenuOrderDTO createMenuOrder(@Valid @RequestBody CreateMenuOrderDTO createMenuOrderDTO) {
        return menuOrderService.createMenuOrder(createMenuOrderDTO);
    }

    @PostMapping("/{orderId}/order-lines")
    @ResponseStatus(HttpStatus.CREATED)
    public void attachOrderLinesToOrder(
            @PathVariable Long orderId,
            @Valid @RequestBody List<CreateMenuOrderDTO.MenuItemDTO> menuItems
    ) {
        menuOrderService.attachOrderLines(orderId, menuItems);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MenuOrderDTO> getAllOrders() {
        return menuOrderService.getAllOrdersWithLines();
    }


    @GetMapping("/status")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderStatus> getOrderStatus(){
        return Arrays.asList(OrderStatus.values());
    }


    @PatchMapping("/orderIds/status")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateOrderStatus(@RequestBody UpdateOrderStatusDTO updateOrderStatusDTO) {
        menuOrderService.updateOrderStatus(updateOrderStatusDTO);
    }

    @PutMapping("/{orderId}/status")
    public void updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam OrderStatus status
    ) {
        menuOrderService.updateOrderStatus(orderId, status);
    }

    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable Long orderId) {
        menuOrderService.deleteOrderById(orderId);
    }

    @GetMapping("/grouped")
    public List<OrderDTO> getGroupedOrders(
            @RequestParam(required = false) Long tableNumber,
            @RequestParam(required = false) Long roomNumber
    ) {
        return menuOrderService.groupByTableOrRoom(tableNumber, roomNumber);
    }

    @GetMapping("/all/table/{tableNumber}")
    public List<MenuOrderSummarized> getAllOrdersByTable(
            @PathVariable Long tableNumber
    ) {
        return menuOrderService.findAllOrdersByTable(tableNumber);
    }

    @GetMapping("/all/room/{roomNumber}")
    public List<MenuOrderSummarized> getAllOrdersByRoom(
            @PathVariable Long roomNumber
    ) {
        return menuOrderService.findAllOrdersByRoom(roomNumber);
    }
}

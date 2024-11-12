package com.fresh.coding.sooatelapi.controllers.menus.orders;

import com.fresh.coding.sooatelapi.dtos.menu.orders.CreateMenuOrderDTO;
import com.fresh.coding.sooatelapi.dtos.menu.orders.MenuOrderDTO;
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
        System.out.println(createMenuOrderDTO);
        return menuOrderService.createMenuOrder(createMenuOrderDTO);
    }

    @GetMapping("/status")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderStatus> getOrderStatus(){
        return Arrays.asList(OrderStatus.values());
    }

    @PatchMapping("/{orderId}/status")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateOrderStatus(@PathVariable Long orderId, @RequestBody OrderStatus newStatus) {
        menuOrderService.updateOrderStatus(orderId, newStatus);
    }

}

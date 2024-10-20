package com.fresh.coding.sooatelapi.controllers.menus.orders;

import com.fresh.coding.sooatelapi.dtos.menu.orders.MenuOrderDTO;
import com.fresh.coding.sooatelapi.dtos.menu.orders.MenuOrderSummarized;
import com.fresh.coding.sooatelapi.services.menus.orders.MenuOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/menu-orders")
@RequiredArgsConstructor
public class MenuOrderController {
    private final MenuOrderService menuOrderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MenuOrderSummarized createMenuOrder(@Valid @RequestBody  MenuOrderDTO menuOrderDTO) {
        return menuOrderService.createMenuOrder(menuOrderDTO);
    }

}

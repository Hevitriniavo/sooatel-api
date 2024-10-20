package com.fresh.coding.sooatelapi.controllers.menus.orders;

import com.fresh.coding.sooatelapi.dtos.menu.orders.MenuOrderSummarized;
import com.fresh.coding.sooatelapi.dtos.pagination.Paginate;
import com.fresh.coding.sooatelapi.dtos.searchs.MenuOrderSearch;
import com.fresh.coding.sooatelapi.services.menus.orders.SearchMenuOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu-orders")
@RequiredArgsConstructor
public class SearchMenuOrderController {
    private final SearchMenuOrderService searchMenuOrderService;

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public Paginate<List<MenuOrderSummarized>> searchMenuOrders(
            @ModelAttribute MenuOrderSearch searchCriteria,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return searchMenuOrderService.searchMenuOrders(searchCriteria, pageable);
    }
}

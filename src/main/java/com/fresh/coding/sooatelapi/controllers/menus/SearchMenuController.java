package com.fresh.coding.sooatelapi.controllers.menus;

import com.fresh.coding.sooatelapi.dtos.menus.MenusWithCategorySummarized;
import com.fresh.coding.sooatelapi.dtos.pagination.Paginate;
import com.fresh.coding.sooatelapi.dtos.searchs.MenuSearch;
import com.fresh.coding.sooatelapi.services.menus.SearchMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/menus")
@RequiredArgsConstructor
public class SearchMenuController {

    private final SearchMenuService searchMenuService;

    @GetMapping
    public Paginate<List<MenusWithCategorySummarized>> getAllMenus(
            @ModelAttribute MenuSearch menuSearch,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return searchMenuService.findAllMenus(menuSearch, page, size);
    }

}

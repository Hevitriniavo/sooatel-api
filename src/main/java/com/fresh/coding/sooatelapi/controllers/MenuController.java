package com.fresh.coding.sooatelapi.controllers;

import com.fresh.coding.sooatelapi.dtos.menus.MenuSummarized;
import com.fresh.coding.sooatelapi.dtos.menus.SaveMenu;
import com.fresh.coding.sooatelapi.services.menus.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @PostMapping
    public MenuSummarized save(@Valid @RequestBody SaveMenu toSave) {
        return menuService.save(toSave);
    }

    @GetMapping("/all")
    public List<MenuSummarized> getAllMenus() {
        return menuService.findALlMenus();
    }

}

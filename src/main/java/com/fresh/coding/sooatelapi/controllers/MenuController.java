package com.fresh.coding.sooatelapi.controllers;

import com.fresh.coding.sooatelapi.dtos.menus.MenuSummarized;
import com.fresh.coding.sooatelapi.dtos.menus.SaveMenu;
import com.fresh.coding.sooatelapi.enums.MenuStatus;
import com.fresh.coding.sooatelapi.services.menus.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
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

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMenu(@PathVariable Long id) {
        menuService.deleteById(id);
    }

    @GetMapping("/all")
    public List<MenuSummarized> getAllMenus() {
        return menuService.findALlMenus();
    }

    @GetMapping("/status")
    public List<MenuStatus> getAllStatusMenus() {
        return Arrays.asList(MenuStatus.values());
    }

    @PutMapping("/{id}/status")
    public MenuSummarized updateMenuStatus(
            @PathVariable Long id,
            @RequestParam(name = "status")
            MenuStatus status) {
        return menuService.updateMenuStatus(id, status);
    }
}

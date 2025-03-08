package com.fresh.coding.sooatelapi.controllers;

import com.fresh.coding.sooatelapi.dtos.MostSoldMenuDTO;
import com.fresh.coding.sooatelapi.services.MostSoldMenusService;
import jakarta.validation.constraints.PastOrPresent;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class MostSoldMenusController {

    private final MostSoldMenusService menuService;

    @GetMapping("/most-sold-menus")
    public List<MostSoldMenuDTO> getMostSoldMenus(
            @RequestParam(required = false)
            @PastOrPresent(message = "The date cannot be in the future")
            LocalDate date
    ) {
        if (date == null) {
            date = LocalDate.now();
        }
        return menuService.findMostSoldMenusByCategory(date);
    }
}

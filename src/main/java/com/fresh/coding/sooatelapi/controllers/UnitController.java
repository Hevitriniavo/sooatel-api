package com.fresh.coding.sooatelapi.controllers;

import com.fresh.coding.sooatelapi.dtos.unit.CreateUnit;
import com.fresh.coding.sooatelapi.dtos.unit.UnitSummarized;
import com.fresh.coding.sooatelapi.services.UnitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/units")
@RequiredArgsConstructor
public class UnitController {

    private final UnitService unitService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UnitSummarized createUnit(@RequestBody @Valid CreateUnit toCreate) {
        return unitService.create(toCreate);
    }

    @GetMapping
    public List<UnitSummarized> getAllUnits() {
        return unitService.getAllUnits();
    }
}

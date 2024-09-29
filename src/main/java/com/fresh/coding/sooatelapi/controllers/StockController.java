package com.fresh.coding.sooatelapi.controllers;

import com.fresh.coding.sooatelapi.dtos.StockPurchaseDto;
import com.fresh.coding.sooatelapi.services.stocks.StockAndCreatePurchase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stocks")
@Validated
@RequiredArgsConstructor
public class StockController {

    private final StockAndCreatePurchase stockAndCreatePurchase;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.OK)
    public void addStockAndCreatePurchase(
            @Valid @RequestBody
            StockPurchaseDto stockPurchaseDto
    ) {
        stockAndCreatePurchase.add(stockPurchaseDto);
    }
}

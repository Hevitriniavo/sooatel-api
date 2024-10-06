package com.fresh.coding.sooatelapi.controllers;

import com.fresh.coding.sooatelapi.dtos.StockPurchaseDto;
import com.fresh.coding.sooatelapi.dtos.pagination.Paginate;
import com.fresh.coding.sooatelapi.dtos.searchs.StockSearch;
import com.fresh.coding.sooatelapi.dtos.stocks.StockSummarized;
import com.fresh.coding.sooatelapi.services.stocks.StockAndCreatePurchase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stocks")
@Validated
@RequiredArgsConstructor
public class StockController {

    private final StockAndCreatePurchase stockAndCreatePurchase;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addStockAndCreatePurchase(
            @Valid @RequestBody
            StockPurchaseDto stockPurchaseDto
    ) {
        stockAndCreatePurchase.add(stockPurchaseDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Paginate<List<StockSummarized>> getAllIngredients(
            @ModelAttribute StockSearch stockSearch,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return stockAndCreatePurchase.findAllStock(
                stockSearch,
                page,
                size
        );
    }
}

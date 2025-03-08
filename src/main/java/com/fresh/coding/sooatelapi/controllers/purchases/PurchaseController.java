package com.fresh.coding.sooatelapi.controllers.purchases;


import com.fresh.coding.sooatelapi.dtos.pagination.Paginate;
import com.fresh.coding.sooatelapi.dtos.purchases.PurchaseDTO;
import com.fresh.coding.sooatelapi.dtos.searchs.PurchaseSearch;
import com.fresh.coding.sooatelapi.services.purchases.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchases")
@RequiredArgsConstructor
public class PurchaseController {
    private final PurchaseService purchaseService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Paginate<List<PurchaseDTO>> getAllPurchases(
            @ModelAttribute PurchaseSearch purchaseSearch,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return purchaseService.findAllPurchases(purchaseSearch, page, size);
    }

    @GetMapping("/{ingredientId}/fifo-cost")
    public Double getFIFOCost(@PathVariable Long ingredientId) {
        return purchaseService.calculateFIFOCost(ingredientId);
    }
}

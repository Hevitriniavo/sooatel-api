package com.fresh.coding.sooatelapi.controllers.operations;

import com.fresh.coding.sooatelapi.dtos.operations.OperationSummarized;
import com.fresh.coding.sooatelapi.dtos.operations.OperationWithStock;
import com.fresh.coding.sooatelapi.dtos.pagination.Paginate;
import com.fresh.coding.sooatelapi.dtos.searchs.OperationSearch;
import com.fresh.coding.sooatelapi.dtos.searchs.TotalStockQuery;
import com.fresh.coding.sooatelapi.dtos.statistc.TotalStock;
import com.fresh.coding.sooatelapi.services.operations.OperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/operations")
@RequiredArgsConstructor
public class OperationController {
    private final OperationService operationService;
    @GetMapping
    public Paginate<List<OperationSummarized>> getAllOperations(
            @ModelAttribute OperationSearch search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return operationService.findAllOperations(search, page, size);
    }


    @GetMapping("/details/{stockId}")
    public OperationWithStock getOperationDetailByStockId(
            @PathVariable Long stockId
    ){
        return operationService.findOperationDetailByStockId(stockId);
    }


    @GetMapping("/statistic")
    public List<TotalStock> getOperationDetailByStockId(
            @ModelAttribute TotalStockQuery query
    ){
        return operationService.getTotalStocks(query);
    }
}

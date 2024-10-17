package com.fresh.coding.sooatelapi.services.operations;

import com.fresh.coding.sooatelapi.dtos.operations.OperationSummarized;
import com.fresh.coding.sooatelapi.dtos.operations.OperationWithStock;
import com.fresh.coding.sooatelapi.dtos.pagination.Paginate;
import com.fresh.coding.sooatelapi.dtos.searchs.OperationSearch;
import com.fresh.coding.sooatelapi.dtos.searchs.TotalStockQuery;
import com.fresh.coding.sooatelapi.dtos.statistc.TotalStock;

import java.util.List;

public interface OperationService {
    Paginate<List<OperationSummarized>> findAllOperations(OperationSearch search, int page, int size);

    OperationWithStock findOperationDetailByStockId(Long stockId);

    List<TotalStock> getTotalStocks(TotalStockQuery query);
}

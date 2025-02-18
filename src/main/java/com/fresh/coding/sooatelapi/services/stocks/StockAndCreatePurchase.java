package com.fresh.coding.sooatelapi.services.stocks;

import com.fresh.coding.sooatelapi.dtos.StockPurchaseDto;
import com.fresh.coding.sooatelapi.dtos.pagination.Paginate;
import com.fresh.coding.sooatelapi.dtos.searchs.StockSearch;
import com.fresh.coding.sooatelapi.dtos.stocks.StockSummarized;

import java.util.List;

public interface StockAndCreatePurchase {
    void add(StockPurchaseDto stockPurchaseDto);

    Paginate<List<StockSummarized>> findAllStock(StockSearch stockSearch, int page, int size);
}

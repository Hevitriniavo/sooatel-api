package com.fresh.coding.sooatelapi.services.operations;

import com.fresh.coding.sooatelapi.dtos.operations.OperationSummarized;
import com.fresh.coding.sooatelapi.dtos.pagination.Paginate;
import com.fresh.coding.sooatelapi.dtos.searchs.OperationSearch;

import java.util.List;

public interface OperationService {
    Paginate<List<OperationSummarized>> findAllOperations(OperationSearch search, int page, int size);
}

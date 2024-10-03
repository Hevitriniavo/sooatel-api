package com.fresh.coding.sooatelapi.services.tables;


import com.fresh.coding.sooatelapi.dtos.pagination.Paginate;
import com.fresh.coding.sooatelapi.dtos.searchs.TableSearch;
import com.fresh.coding.sooatelapi.dtos.tables.TableSummarized;

import java.util.List;

public interface SearchTableService {
    Paginate<List<TableSummarized>> findAllTables(TableSearch tableSearch, int page, int size);
}

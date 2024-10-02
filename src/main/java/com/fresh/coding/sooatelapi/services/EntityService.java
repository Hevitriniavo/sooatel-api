package com.fresh.coding.sooatelapi.services;

import com.fresh.coding.sooatelapi.dtos.categories.CategorySummarized;
import com.fresh.coding.sooatelapi.dtos.pagination.Paginate;
import com.fresh.coding.sooatelapi.dtos.searchs.CategorySearch;
import com.fresh.coding.sooatelapi.dtos.searchs.UnitSearch;
import com.fresh.coding.sooatelapi.dtos.unit.UnitSummarized;

import java.util.List;

public interface EntityService {

    Paginate<List<CategorySummarized>> findAllCategories(CategorySearch categorySearch, int page, int size);

    Paginate<List<UnitSummarized>> getAllUnits(UnitSearch search, int page, int size);

}

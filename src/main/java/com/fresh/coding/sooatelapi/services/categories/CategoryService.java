package com.fresh.coding.sooatelapi.services.categories;

import com.fresh.coding.sooatelapi.dtos.categories.CategorySummarized;
import com.fresh.coding.sooatelapi.dtos.categories.SaveCategory;
import com.fresh.coding.sooatelapi.dtos.pagination.Paginate;
import com.fresh.coding.sooatelapi.dtos.searchs.CategorySearch;

import java.util.List;

public interface CategoryService {
    CategorySummarized save(SaveCategory toSave);

    List<CategorySummarized> findAllCategories();

    Paginate<List<CategorySummarized>> findAllCategories(CategorySearch categorySearch, int page, int size);

    void deleteById(Long id);
}

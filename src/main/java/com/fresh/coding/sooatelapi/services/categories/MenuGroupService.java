package com.fresh.coding.sooatelapi.services.categories;

import com.fresh.coding.sooatelapi.dtos.categories.MenuGroupSummarized;
import com.fresh.coding.sooatelapi.dtos.categories.SaveCategory;
import com.fresh.coding.sooatelapi.dtos.pagination.Paginate;
import com.fresh.coding.sooatelapi.dtos.searchs.CategorySearch;

import java.util.List;

public interface MenuGroupService {
    MenuGroupSummarized save(SaveCategory toSave);

    List<MenuGroupSummarized> findAllCategories();

    Paginate<List<MenuGroupSummarized>> findAllCategories(CategorySearch categorySearch, int page, int size);

    void deleteById(Long id);
}

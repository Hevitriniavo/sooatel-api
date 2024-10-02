package com.fresh.coding.sooatelapi.services.categories;

import com.fresh.coding.sooatelapi.dtos.categories.CategorySummarized;
import com.fresh.coding.sooatelapi.dtos.categories.SaveCategory;

public interface CategoryService {
    CategorySummarized save(SaveCategory toSave);
}

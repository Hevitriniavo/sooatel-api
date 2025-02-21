package com.fresh.coding.sooatelapi.services;

import com.fresh.coding.sooatelapi.dtos.categories.ingredients.GroupSummarized;

import java.util.List;

public interface IngredientsByGroupService {
    List<GroupSummarized> findStocksByIngredientsGroup();
}

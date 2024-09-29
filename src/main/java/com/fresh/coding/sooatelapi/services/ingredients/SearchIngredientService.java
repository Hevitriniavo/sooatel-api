package com.fresh.coding.sooatelapi.services.ingredients;

import com.fresh.coding.sooatelapi.dtos.ingredients.IngredientSummarizedWithUnitName;
import com.fresh.coding.sooatelapi.dtos.pagination.Paginate;

import java.util.List;

public interface SearchIngredientService {
    Paginate<List<IngredientSummarizedWithUnitName>> findAllIngredient(String ingredientName, String unitName, int page, int size);
}

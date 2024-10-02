package com.fresh.coding.sooatelapi.services.ingredients;

import com.fresh.coding.sooatelapi.dtos.ingredients.IngredientSummarizedWithUnitName;
import com.fresh.coding.sooatelapi.dtos.pagination.Paginate;
import com.fresh.coding.sooatelapi.dtos.searchs.IngredientSearch;

import java.util.List;

public interface SearchIngredientService {
    Paginate<List<IngredientSummarizedWithUnitName>> findAllIngredient(IngredientSearch ingredientSearch, int page, int size);
}

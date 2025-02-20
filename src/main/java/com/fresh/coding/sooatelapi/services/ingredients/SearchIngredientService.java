package com.fresh.coding.sooatelapi.services.ingredients;

import com.fresh.coding.sooatelapi.dtos.ingredients.IngredientSummarizedWithUnitAndGroup;
import com.fresh.coding.sooatelapi.dtos.pagination.Paginate;
import com.fresh.coding.sooatelapi.dtos.searchs.IngredientSearch;

import java.util.List;

public interface SearchIngredientService {
    Paginate<List<IngredientSummarizedWithUnitAndGroup>> findAllIngredient(IngredientSearch ingredientSearch, int page, int size);
}

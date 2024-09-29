package com.fresh.coding.sooatelapi.services.ingredients;

import com.fresh.coding.sooatelapi.dtos.ingredients.CreateIngredient;
import com.fresh.coding.sooatelapi.dtos.ingredients.IngredientSummarized;
import com.fresh.coding.sooatelapi.dtos.ingredients.UpdateIngredient;
import lombok.NonNull;

import java.util.List;

public interface IngredientService {
    IngredientSummarized create(@NonNull CreateIngredient toCreate);
    IngredientSummarized update(@NonNull UpdateIngredient toUpdate);
    List<IngredientSummarized> findAllIngredients();
    void delete(Long id);
}

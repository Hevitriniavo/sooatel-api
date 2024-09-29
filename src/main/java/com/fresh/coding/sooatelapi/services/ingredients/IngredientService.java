package com.fresh.coding.sooatelapi.services.ingredients;

import com.fresh.coding.sooatelapi.dtos.ingredients.CreateIngredient;
import com.fresh.coding.sooatelapi.dtos.ingredients.IngredientSummarized;
import lombok.NonNull;

public interface IngredientService {
    IngredientSummarized create(@NonNull CreateIngredient toCreate);
}

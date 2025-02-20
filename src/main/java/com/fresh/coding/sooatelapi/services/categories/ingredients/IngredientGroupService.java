package com.fresh.coding.sooatelapi.services.categories.ingredients;

import com.fresh.coding.sooatelapi.dtos.categories.ingredients.IngredientGroupDTO;
import com.fresh.coding.sooatelapi.dtos.categories.ingredients.IngredientGroupSummarized;

import java.util.List;

public interface IngredientGroupService {
    IngredientGroupSummarized create(IngredientGroupDTO groupDTO);

    IngredientGroupSummarized update(Long id, IngredientGroupDTO groupDTO);

    List<IngredientGroupSummarized> all();

    void remove(Long id);
}

package com.fresh.coding.sooatelapi.dtos.ingredients;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
sealed class IngredientBase permits
        CreateIngredient,
        IngredientSummarized,
        IngredientSummarizedWithUnitName,
        UpdateIngredient {

    @NotBlank(message = "The name must not be blank")
    private String name;

}

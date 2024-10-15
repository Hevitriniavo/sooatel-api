package com.fresh.coding.sooatelapi.dtos.menu.ingredients;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuIngredientDto implements Serializable {

    @NotNull(message = "Menu ID cannot be null")
    private Long menuId;

    @NotEmpty(message = "Ingredients list cannot be empty")
    private List<IngredientQuantityDto> ingredients;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class IngredientQuantityDto implements Serializable {

        @NotNull(message = "Ingredient ID cannot be null")
        private Long ingredientId;

        @Min(value = 1, message = "Quantity must be greater than zero")
        private Double quantity;
    }
}

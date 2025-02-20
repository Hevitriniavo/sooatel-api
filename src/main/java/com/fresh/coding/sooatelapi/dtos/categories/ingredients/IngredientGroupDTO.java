package com.fresh.coding.sooatelapi.dtos.categories.ingredients;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class IngredientGroupDTO {
    @NotBlank(message = "The group ingredient cannot be empty.")
    @Size(min = 2, max = 100, message = "The group ingredient must be between 2 and 100 characters.")
    private String name;
}

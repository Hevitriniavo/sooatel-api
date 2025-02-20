package com.fresh.coding.sooatelapi.dtos.categories.ingredients;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IngredientGroupDTO implements Serializable {
    @NotBlank(message = "The group ingredient cannot be empty.")
    @Size(min = 2, max = 100, message = "The group ingredient must be between 2 and 100 characters.")
    private String name;
}

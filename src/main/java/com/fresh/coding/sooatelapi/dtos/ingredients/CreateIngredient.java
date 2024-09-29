package com.fresh.coding.sooatelapi.dtos.ingredients;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public final class CreateIngredient extends IngredientBase implements Serializable {
    @NotNull(message = "The unitId must not be null")
    private Long unitId;

    public CreateIngredient(String name, Long unitId) {
        super(name);
        this.unitId = unitId;
    }
}

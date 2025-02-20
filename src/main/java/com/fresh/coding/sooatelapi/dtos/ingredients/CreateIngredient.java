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

    @NotNull(message = "The groupId must not be null")
    private Long groupId;

    public CreateIngredient(String name, Long unitId, Long groupId) {
        super(name);
        this.unitId = unitId;
        this.groupId =  groupId;
    }
}

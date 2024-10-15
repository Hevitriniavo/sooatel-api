package com.fresh.coding.sooatelapi.dtos.ingredients;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public final class IngredientSummarizedWithUnitName extends IngredientBase implements Serializable {
    private Long id;
    private Long unitId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String unitName;

    public IngredientSummarizedWithUnitName (
            Long id,
            Long unitId,
            String name,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            String unitName
    ) {
        super(name);
        this.id = id;
        this.unitId = unitId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.unitName = unitName;
    }
}

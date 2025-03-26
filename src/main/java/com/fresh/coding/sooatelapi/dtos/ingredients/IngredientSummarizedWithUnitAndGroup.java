package com.fresh.coding.sooatelapi.dtos.ingredients;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public final class IngredientSummarizedWithUnitAndGroup extends IngredientBase implements Serializable {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long unitId;
    private String unitName;
    private Long groupId;
    private String groupName;

    public IngredientSummarizedWithUnitAndGroup(
            Long id,
            String name,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            Long unitId,
            String unitName,
            Long groupId,
            String groupName
    ) {
        super(name);
        this.id = id;
        this.unitId = unitId;
        this.groupId = groupId;
        this.groupName = groupName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.unitName = unitName;
    }
}

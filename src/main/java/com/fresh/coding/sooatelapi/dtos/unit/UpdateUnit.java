package com.fresh.coding.sooatelapi.dtos.unit;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public final class UpdateUnit extends UnitBase implements Serializable {
    @NotNull(message = "ID cannot be null")
    private Long id;

    public UpdateUnit(Long id, String name, String abbreviation) {
        super(name, abbreviation);
        this.id = id;
    }
}

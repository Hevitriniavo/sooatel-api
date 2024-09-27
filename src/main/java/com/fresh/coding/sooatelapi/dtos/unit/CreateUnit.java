package com.fresh.coding.sooatelapi.dtos.unit;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public final class CreateUnit extends UnitBase implements Serializable {
    public CreateUnit(String name, String abbreviation) {
        super(name, abbreviation);
    }
}

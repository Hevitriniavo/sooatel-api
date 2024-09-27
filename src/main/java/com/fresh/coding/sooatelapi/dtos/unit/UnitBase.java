package com.fresh.coding.sooatelapi.dtos.unit;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
sealed class UnitBase permits UnitSummarized, CreateUnit, UpdateUnit {

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Abbreviation cannot be blank")
    @Size(min = 1, max = 10, message = "Abbreviation must be between 1 and 10 characters")
    private String abbreviation;
}

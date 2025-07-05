package com.fresh.coding.sooatelapi.dtos.tables;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
sealed class TableBase permits SaveTable, TableSummarized {

    private Long id;

    @NotNull(message = "Table number cannot be null.")
    @Min(value = 1, message = "Table number must be greater than or equal to 1.")
    private Long number;

    @NotNull(message = "Capacity cannot be null.")
    @Min(value = 1, message = "Capacity must be greater than or equal to 1.")
    private Long capacity;
}

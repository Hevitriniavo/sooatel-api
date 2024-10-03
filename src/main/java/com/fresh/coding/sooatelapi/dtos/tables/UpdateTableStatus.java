package com.fresh.coding.sooatelapi.dtos.tables;

import com.fresh.coding.sooatelapi.enums.TableStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class UpdateTableStatus implements Serializable {
    @NotNull
    private Long id;

    @NotNull
    private TableStatus status;
}
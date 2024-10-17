package com.fresh.coding.sooatelapi.dtos.operations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OperationWithStock {
    private Long stockId;
    private Long ingredientId;
    private String ingredientName;
    private List<OperationSummarized> operations = new ArrayList<>();
}

package com.fresh.coding.sooatelapi.dtos.operations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OperationWithStock implements Serializable  {
    private Long stockId;
    private Long ingredientId;
    private String ingredientName;
    private Double quantity;
    private List<OperationSummarized> operations = new ArrayList<>();
}

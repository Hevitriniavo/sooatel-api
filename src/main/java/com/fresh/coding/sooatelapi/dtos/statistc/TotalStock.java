package com.fresh.coding.sooatelapi.dtos.statistc;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TotalStock {
    private String ingredientName;
    private Double totalQuantity;
    private String unitAbbreviation;
}

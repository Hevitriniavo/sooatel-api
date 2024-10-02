package com.fresh.coding.sooatelapi.dtos.searchs;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class StockSearch implements Serializable {
    private String ingredientName;
    private Double quantityMax;
    private Double quantityMin;
    private LocalDateTime startDate;
    private LocalDateTime startEnd;
}

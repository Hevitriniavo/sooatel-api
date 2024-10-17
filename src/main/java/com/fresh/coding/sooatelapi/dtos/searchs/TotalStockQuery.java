package com.fresh.coding.sooatelapi.dtos.searchs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class TotalStockQuery {
    private LocalDateTime date;
    private Long ingredientId;
    private Double maxTotalQuantity;
    private Double minTotalQuantity;
}

package com.fresh.coding.sooatelapi.dtos.stocks;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class StockSummarized implements Serializable {
    private Long id;
    private Double quantity;
    private String ingredientName;
    private Long ingredientId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

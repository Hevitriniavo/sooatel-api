package com.fresh.coding.sooatelapi.dtos.categories.ingredients;

import com.fresh.coding.sooatelapi.dtos.unit.UnitSummarized;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IngredientWithStockSummarized {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private StockIngredientWithGroup stock;
    private UnitSummarized unit;
}

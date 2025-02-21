package com.fresh.coding.sooatelapi.dtos.categories.ingredients;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockIngredientWithGroup {
    private Long id;
    private Double quantity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

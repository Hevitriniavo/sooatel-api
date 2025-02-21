package com.fresh.coding.sooatelapi.dtos.categories.ingredients;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupSummarized {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @Builder.Default
    private List<IngredientWithStockSummarized> ingredients = new ArrayList<>();
}

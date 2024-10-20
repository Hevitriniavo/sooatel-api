package com.fresh.coding.sooatelapi.dtos.purchases;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDTO {
    private Long purchaseId;
    private String ingredientName;
    private String ingredientId;
    private Double quantity;
    private Double cost;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
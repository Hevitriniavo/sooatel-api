package com.fresh.coding.sooatelapi.dtos.searchs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseSearch {
    private Long ingredientId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}

package com.fresh.coding.sooatelapi.dtos;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class JournalDto  implements Serializable {
    private LocalDate date;
    private Double dailyRevenue;
    private Double dailyIngredientCost;
    private Double dailyNetProfit;
}

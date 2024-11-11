package com.fresh.coding.sooatelapi.dtos.searchs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class TotalStockQuery {
    private LocalDate date;
    private Long ingredientId;
    private Double maxTotalQuantity;
    private Double minTotalQuantity;

    public LocalDateTime getDateAsLocalDateTime() {
        if (date != null) {
            return date.atStartOfDay().plusDays(1).minusSeconds(1);
        }
        return null;
    }

}

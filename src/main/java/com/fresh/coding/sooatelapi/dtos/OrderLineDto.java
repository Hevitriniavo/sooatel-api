package com.fresh.coding.sooatelapi.dtos;

import com.fresh.coding.sooatelapi.dtos.menus.MenuSummarized;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderLineDto {
    private Long id;
    private Long quantity;
    private Long unitPrice;
    private Long totalPrice;
    private MenuSummarized menu;
}

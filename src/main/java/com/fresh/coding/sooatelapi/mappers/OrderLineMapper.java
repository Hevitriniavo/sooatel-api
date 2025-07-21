package com.fresh.coding.sooatelapi.mappers;

import com.fresh.coding.sooatelapi.dtos.OrderLineDto;
import com.fresh.coding.sooatelapi.entities.OrderLine;

public class OrderLineMapper {
    public static OrderLineDto toDto(OrderLine entity) {
        if (entity == null) return null;

        return OrderLineDto.builder()
                .id(entity.getId())
                .quantity(entity.getQuantity())
                .unitPrice(entity.getUnitPrice())
                .totalPrice(entity.getTotalPrice())
                .menu(MenuMapper.toSummarized(entity.getMenu()))
                .build();
    }
}

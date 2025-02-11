package com.fresh.coding.sooatelapi.dtos.menus;

import com.fresh.coding.sooatelapi.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UpdateOrderStatusDTO implements Serializable {
    private List<Long> orderIds;
    private OrderStatus orderStatus;
}

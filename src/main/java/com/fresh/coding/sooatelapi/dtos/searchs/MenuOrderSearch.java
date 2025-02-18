package com.fresh.coding.sooatelapi.dtos.searchs;

import com.fresh.coding.sooatelapi.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
public class MenuOrderSearch implements Serializable {
    private LocalDate orderDate;
    private OrderStatus status;
    private Long customerId;
    private Long roomId;
    private Long tableId;
}

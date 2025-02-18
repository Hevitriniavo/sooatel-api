package com.fresh.coding.sooatelapi.dtos.menu.orders;

import com.fresh.coding.sooatelapi.dtos.customers.CustomerDTO;
import com.fresh.coding.sooatelapi.dtos.menus.MenuSummarized;
import com.fresh.coding.sooatelapi.dtos.rooms.RoomDTO;
import com.fresh.coding.sooatelapi.dtos.tables.TableSummarized;
import com.fresh.coding.sooatelapi.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
public class MenuOrderSummarized  implements Serializable {
    private Long id;
    private CustomerDTO customer;
    private RoomDTO room;
    private TableSummarized table;
    private LocalDateTime orderDate;
    private Double quantity;
    private Double cost;
    private OrderStatus orderStatus;
    private MenuSummarized menu;
    private LocalDateTime updatedAt;
    private Long paymentId;
}

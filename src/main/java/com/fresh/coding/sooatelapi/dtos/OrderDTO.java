package com.fresh.coding.sooatelapi.dtos;

import com.fresh.coding.sooatelapi.dtos.rooms.RoomDTO;
import com.fresh.coding.sooatelapi.dtos.tables.TableSummarized;
import com.fresh.coding.sooatelapi.enums.OrderStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {
    private Long id;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private List<OrderLineDto> orderLines;
    private SessionOccupationDTO sessionOccupation;
    private RoomDTO room;
    private TableSummarized table;
}

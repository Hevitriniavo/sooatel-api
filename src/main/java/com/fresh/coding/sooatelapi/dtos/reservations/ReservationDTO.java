package com.fresh.coding.sooatelapi.dtos.reservations;

import com.fresh.coding.sooatelapi.dtos.customers.CustomerDTO;
import com.fresh.coding.sooatelapi.dtos.rooms.RoomDTO;
import com.fresh.coding.sooatelapi.dtos.tables.TableSummarized;
import com.fresh.coding.sooatelapi.enums.ReservationStatus;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationDTO implements Serializable {
    private Long id;
    private CustomerDTO customer;
    private String description;
    private LocalDateTime reservationStart;
    private LocalDateTime reservationEnd;
    private ReservationStatus status;
    @Builder.Default
    private List<RoomDTO> rooms = new ArrayList<>();
    @Builder.Default
    private List<TableSummarized> tables = new ArrayList<>();
}

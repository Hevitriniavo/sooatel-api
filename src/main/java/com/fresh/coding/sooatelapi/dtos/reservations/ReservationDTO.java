package com.fresh.coding.sooatelapi.dtos.reservations;

import com.fresh.coding.sooatelapi.dtos.customers.CustomerDTO;
import com.fresh.coding.sooatelapi.dtos.rooms.RoomDTO;
import com.fresh.coding.sooatelapi.dtos.tables.TableSummarized;
import com.fresh.coding.sooatelapi.enums.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO implements Serializable {
    private Long id;
    private CustomerDTO customer;
    private String description;
    private LocalDateTime reservationStart;
    private LocalDateTime reservationEnd;
    private ReservationStatus status;
    private RoomDTO room;
    private TableSummarized table;
}

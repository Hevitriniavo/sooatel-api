package com.fresh.coding.sooatelapi.dtos.reservations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaveReservationDTO {
    private LocalDateTime reservationStart;
    private LocalDateTime reservationEnd;
    private Long customerId;
    private List<Long> roomIds;
    private List<Long> tableIds;
    private String status;
    private String description;
}

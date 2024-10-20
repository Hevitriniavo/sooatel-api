package com.fresh.coding.sooatelapi.dtos.reservations;

import com.fresh.coding.sooatelapi.enums.ReservationStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaveReservationDTO {

    private Long id;

    @NotNull(message = "Customer ID cannot be null")
    private Long customerId;

    @NotEmpty(message = "Description cannot be empty")
    private String description;

    @NotNull(message = "Reservation start time cannot be null")
    private LocalDateTime reservationStart;

    @NotNull(message = "Reservation end time cannot be null")
    private LocalDateTime reservationEnd;

    @NotNull(message = "Status cannot be null")
    private ReservationStatus status;

    private Long roomId;

    private Long tableId;
}

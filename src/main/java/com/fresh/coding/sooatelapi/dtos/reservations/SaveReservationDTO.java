package com.fresh.coding.sooatelapi.dtos.reservations;

import com.fresh.coding.sooatelapi.dtos.customers.CustomerCreateDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaveReservationDTO implements Serializable {
    private LocalDateTime reservationStart;
    private LocalDateTime reservationEnd;
    private CustomerCreateDTO customer;
    private List<Long> roomIds;
    private List<Long> tableIds;
    private String status;
    private String description;
}

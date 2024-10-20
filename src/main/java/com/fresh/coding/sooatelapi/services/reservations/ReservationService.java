package com.fresh.coding.sooatelapi.services.reservations;

import com.fresh.coding.sooatelapi.dtos.reservations.ReservationDTO;
import com.fresh.coding.sooatelapi.dtos.reservations.SaveReservationDTO;

public interface ReservationService {
    ReservationDTO saveReservation(SaveReservationDTO saveReservationDTO);
}

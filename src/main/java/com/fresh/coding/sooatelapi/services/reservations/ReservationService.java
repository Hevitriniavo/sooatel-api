package com.fresh.coding.sooatelapi.services.reservations;

import com.fresh.coding.sooatelapi.dtos.reservations.ReservationDTO;
import com.fresh.coding.sooatelapi.dtos.reservations.SaveReservationDTO;
import com.fresh.coding.sooatelapi.enums.ReservationStatus;

import java.util.List;

public interface ReservationService {
    ReservationDTO saveReservation(SaveReservationDTO saveReservationDTO);

    List<ReservationDTO> findAllReservations();

    void deleteReservation(Long id);

    ReservationDTO updateReservation(Long id, SaveReservationDTO saveReservationDTO);

    ReservationDTO updateReservationStatus(Long id, ReservationStatus status);
}

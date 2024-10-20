package com.fresh.coding.sooatelapi.controllers.reservations;

import com.fresh.coding.sooatelapi.dtos.reservations.ReservationDTO;
import com.fresh.coding.sooatelapi.dtos.reservations.SaveReservationDTO;
import com.fresh.coding.sooatelapi.enums.ReservationStatus;
import com.fresh.coding.sooatelapi.services.reservations.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@Validated
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationDTO saveReservation(@RequestBody SaveReservationDTO saveReservationDTO) {
        return reservationService.saveReservation(saveReservationDTO);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/status")
    public List<ReservationStatus> getReservationStatus(){
        return Arrays.asList(ReservationStatus.values());
    }
}

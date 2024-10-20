package com.fresh.coding.sooatelapi.repositories;

import com.fresh.coding.sooatelapi.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

}

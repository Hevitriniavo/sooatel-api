package com.fresh.coding.sooatelapi.repositories;

import com.fresh.coding.sooatelapi.entities.Reservation;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Room r SET r.reservations = NULL WHERE r.id = :roomId")
    void unsetRoomReservationById(Long roomId);

    @Modifying
    @Transactional
    @Query("UPDATE TableEntity t SET t.reservations = NULL WHERE t.id = :tableId")
    void unsetTableReservationById(Long tableId);

}

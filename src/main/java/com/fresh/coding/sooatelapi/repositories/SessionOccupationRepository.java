package com.fresh.coding.sooatelapi.repositories;

import com.fresh.coding.sooatelapi.entities.SessionOccupation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionOccupationRepository extends JpaRepository<SessionOccupation, Long> {
    Optional<SessionOccupation> findByTableIdAndEndedAtIsNull(Long tableId);
    Optional<SessionOccupation> findByRoomIdAndEndedAtIsNull(Long roomId);

}

package com.fresh.coding.sooatelapi.repositories;

import com.fresh.coding.sooatelapi.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByFloorId(Long floorId);

    @Query("SELECT r FROM Room r WHERE r.number = :number")
    Optional<Room> findByRoomNumber(Long number);

    @Query("SELECT DISTINCT r FROM Room r JOIN r.orders o JOIN o.orderLines ol")
    List<Room> findRoomsWithMenuOrders();
}

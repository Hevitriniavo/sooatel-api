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


    @Transactional
    @Modifying
    @Query("DELETE FROM Room r WHERE r.floor.id = :id")
    void deleteRoomByFloorId(Long id);


    @Query("SELECT r FROM Room r LEFT JOIN FETCH r.menuOrders WHERE r.menuOrders IS NOT EMPTY")
    List<Room> findRoomsWithMenuOrders();


    Optional<Room> findByRoomNumber(Integer roomNumber);
}

package com.fresh.coding.sooatelapi.repositories;

import com.fresh.coding.sooatelapi.entities.MenuOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MenuOrderRepository extends JpaRepository<MenuOrder, Long> {
    Optional<MenuOrder> findByTableNumber(Integer tableNumber);
    Optional<MenuOrder> findByRoomRoomNumber(Integer roomNumber);
}

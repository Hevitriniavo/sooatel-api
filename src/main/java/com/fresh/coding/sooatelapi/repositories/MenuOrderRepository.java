package com.fresh.coding.sooatelapi.repositories;

import com.fresh.coding.sooatelapi.entities.MenuOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface MenuOrderRepository extends JpaRepository<MenuOrder, Long> {
    @Query("SELECT m FROM MenuOrder m LEFT JOIN FETCH m.room r WHERE r.roomNumber = :roomNumber")
    List<MenuOrder> findAllByRoomNumber(Integer roomNumber);

    @Query("SELECT m FROM MenuOrder m LEFT JOIN FETCH m.table t WHERE t.number = :tableNumber")
    List<MenuOrder> findAllByTableNumber(Integer tableNumber);

    @Query("SELECT m FROM MenuOrder m LEFT JOIN FETCH m.room r LEFT JOIN FETCH m.table t WHERE r.roomNumber = :roomNumber OR t.number = :tableNumber")
    List<MenuOrder> findAllByRoomNumberOrTableNumber(Integer roomNumber, Integer tableNumber);

    List<MenuOrder> findAllByTableNumberIn(Collection<Integer> tableNumbers);

    List<MenuOrder> findAllByRoomRoomNumberIn(Collection<Integer> roomRoomNumbers);

    List<MenuOrder> findAllByPaymentId(Long paymentId);
}

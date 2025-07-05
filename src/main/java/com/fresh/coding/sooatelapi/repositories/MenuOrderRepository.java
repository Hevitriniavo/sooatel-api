package com.fresh.coding.sooatelapi.repositories;

import com.fresh.coding.sooatelapi.dtos.MostMenu;
import com.fresh.coding.sooatelapi.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Repository
public interface MenuOrderRepository extends JpaRepository<Order, Long> {

    @Query("""
    SELECT new com.fresh.coding.sooatelapi.dtos.MostMenu(
        m.id,
        m.name,
        m.description,
        SUM(ol.unitPrice * ol.quantity),
        g.id,
        g.name,
        SUM(ol.quantity)
    )
    FROM Order o
    JOIN o.orderLines ol
    JOIN ol.menu m
    JOIN m.menuGroup g
    WHERE DATE(o.orderDate) = :date
    GROUP BY m.id, m.name, m.description, g.id, g.name
    ORDER BY SUM(ol.quantity) DESC
""")
    List<MostMenu> findMostSoldMenusByDate(@Param("date") LocalDate date);

    List<Order> findAllByTableId(Long tableId);

    List<Order> findAllByRoomId(Long roomId);

}

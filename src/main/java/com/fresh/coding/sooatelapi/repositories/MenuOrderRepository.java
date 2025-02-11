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

    @Query(value = """
        WITH IngredientUsage AS (
            SELECT
                mo.id AS order_id,
                mo.order_date,
                mi.ingredient_id,
                mi.quantity * mo.quantity AS total_used
            FROM menu_order mo
                JOIN menu_ingredient mi ON mo.menu_id = mi.menu_id
        ),
        FIFO_Cost AS (
            SELECT
                iu.order_id,
                iu.order_date,
                p.ingredient_id,
                p.cost AS unit_cost,
                LEAST(iu.total_used, p.quantity) AS used_quantity,
                (LEAST(iu.total_used, p.quantity) * p.cost) AS total_cost
            FROM IngredientUsage iu
                JOIN purchase p ON iu.ingredient_id = p.ingredient_id
            WHERE p.created_at <= iu.order_date
            ORDER BY p.created_at ASC
        )
        SELECT
            DATE(mo.order_date) AS date,
            SUM(mo.cost) AS daily_revenue,
            SUM(fc.total_cost) AS daily_ingredient_cost,
            (SUM(mo.cost) - SUM(fc.total_cost)) AS daily_net_profit
        FROM menu_order mo
            LEFT JOIN FIFO_Cost fc ON mo.id = fc.order_id
        GROUP BY DATE(mo.order_date)
        ORDER BY DATE(mo.order_date) DESC
        """, nativeQuery = true)
    List<Object[]> findDailyRevenueAndCosts();
}

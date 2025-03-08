package com.fresh.coding.sooatelapi.repositories;

import com.fresh.coding.sooatelapi.entities.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    @Query(value = """
        WITH purchase_data AS (
            SELECT
                id,
                ingredient_id,
                quantity,
                cost,
                created_at,
                SUM(quantity) OVER (ORDER BY created_at) AS cumulative_quantity
            FROM purchase
            WHERE ingredient_id = :ingredientId
        ),
        stock_data AS (
            SELECT
                ingredient_id,
                SUM(quantity) AS total_stock
            FROM stock
            WHERE ingredient_id = :ingredientId
            GROUP BY ingredient_id
        ),
        consumption_data AS (
            SELECT
                p.id,
                p.ingredient_id,
                p.quantity,
                p.cost,
                p.created_at,
                p.cumulative_quantity,
                LEAST(p.quantity, GREATEST(s.total_stock - COALESCE(LAG(p.cumulative_quantity, 1, 0) OVER (), 0))) AS consumed_quantity
            FROM purchase_data p
            CROSS JOIN stock_data s
        )
        SELECT
            SUM(consumed_quantity * cost) AS total_cost
        FROM consumption_data
        """, nativeQuery = true)
    Double calculateFIFOCost(@Param("ingredientId") Long ingredientId);
}

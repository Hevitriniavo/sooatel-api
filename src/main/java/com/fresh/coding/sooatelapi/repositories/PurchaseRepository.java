package com.fresh.coding.sooatelapi.repositories;

import com.fresh.coding.sooatelapi.entities.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    @Query(value = """
            WITH ordered_purchases AS (
                SELECT
                    p.ingredient_id,
                    p.quantity AS purchase_quantity,
                    p.cost AS purchase_cost,
                    p.created_at,
                    SUM(p.quantity) OVER (PARTITION BY p.ingredient_id ORDER BY p.created_at) AS cumulative_quantity
                FROM purchase p
                WHERE p.ingredient_id = :ingredientId
            ),
                 menu_ingredient_usage AS (
                     SELECT
                         mi.ingredient_id,
                         SUM(mi.quantity * mo.quantity) AS total_used_quantity
                     FROM menu_ingredient mi
                              JOIN menu_order mo ON mi.menu_id = mo.menu_id
                     WHERE mi.ingredient_id = :ingredientId
                     GROUP BY mi.ingredient_id
                 ),
                 remaining_ingredients AS (
                     SELECT
                         op.ingredient_id,
                         op.purchase_quantity,
                         op.purchase_cost,
                         op.created_at,
                         op.cumulative_quantity,
                         GREATEST(op.cumulative_quantity - COALESCE(miu.total_used_quantity, 0), 0) AS remaining_quantity
                     FROM ordered_purchases op
                              LEFT JOIN menu_ingredient_usage miu ON op.ingredient_id = miu.ingredient_id
                 )
            SELECT
                SUM(ri.remaining_quantity * ri.purchase_cost) AS total_stock_cost
            FROM remaining_ingredients ri
            WHERE ri.remaining_quantity > 0
        """, nativeQuery = true)
    Double calculateFIFOCost(@Param("ingredientId") Long ingredientId);
}

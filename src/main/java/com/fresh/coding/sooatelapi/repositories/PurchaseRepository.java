package com.fresh.coding.sooatelapi.repositories;

import com.fresh.coding.sooatelapi.entities.Purchase;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Purchase p  WHERE p.ingredient.id = :ingredientId")
    int setIngredientToNullByIngredientId(@Param("ingredientId") Long ingredientId);
}

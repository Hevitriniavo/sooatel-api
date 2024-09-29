package com.fresh.coding.sooatelapi.repositories;

import com.fresh.coding.sooatelapi.entities.Ingredient;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Ingredient i SET i.unit = null WHERE i.unit.id = :unitId")
    int setUnitToNullByUnitId(@Param("unitId") Long unitId);
}

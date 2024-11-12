package com.fresh.coding.sooatelapi.repositories;

import com.fresh.coding.sooatelapi.entities.MenuIngredient;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;



@Repository
public interface MenuIngredientRepository extends JpaRepository<MenuIngredient, Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM MenuIngredient mi WHERE mi.menu.id = :menuId AND mi.ingredient.id = :ingredientId")
    void deleteAllByMenuIdAndIngredientId(@Param("menuId") Long menuId, @Param("ingredientId") Long ingredientId);

    boolean existsByMenuIdAndIngredientId(Long menuId, Long ingredientId);
}


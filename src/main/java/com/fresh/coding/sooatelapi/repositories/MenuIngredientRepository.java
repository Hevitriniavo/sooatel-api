package com.fresh.coding.sooatelapi.repositories;

import com.fresh.coding.sooatelapi.entities.MenuIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MenuIngredientRepository extends JpaRepository<MenuIngredient, Long> {
    @Query("""
                  SELECT DISTINCT mi FROM MenuIngredient mi INNER JOIN mi.ingredient i LEFT JOIN i.unit WHERE mi.menu.id = :menuId
            """)
    List<MenuIngredient> findAllIngredientWithMenuIngredient(Long menuId);
}

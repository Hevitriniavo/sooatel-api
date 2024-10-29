package com.fresh.coding.sooatelapi.repositories;

import com.fresh.coding.sooatelapi.entities.MenuIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MenuIngredientRepository extends JpaRepository<MenuIngredient, Long> {

    Optional<MenuIngredient> findByMenuIdAndIngredientId(Long menuId, Long ingredientId);

}

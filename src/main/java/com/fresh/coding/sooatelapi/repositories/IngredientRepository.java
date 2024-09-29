package com.fresh.coding.sooatelapi.repositories;

import com.fresh.coding.sooatelapi.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}

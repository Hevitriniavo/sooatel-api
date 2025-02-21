package com.fresh.coding.sooatelapi.repositories;

import com.fresh.coding.sooatelapi.entities.IngredientGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface IngredientGroupRepository extends JpaRepository<IngredientGroup, Long>,
        JpaSpecificationExecutor<IngredientGroup> {

    @Query("""
      SELECT DISTINCT ig
      FROM IngredientGroup ig
      LEFT JOIN FETCH ig.ingredients i
      LEFT JOIN FETCH i.unit
      LEFT JOIN FETCH i.stock
    """)
    List<IngredientGroup> getStocksByIngredientsGroup();


}

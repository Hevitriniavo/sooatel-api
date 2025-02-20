package com.fresh.coding.sooatelapi.repositories;

import com.fresh.coding.sooatelapi.entities.IngredientGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface IngredientGroupRepository extends JpaRepository<IngredientGroup, Long>,
        JpaSpecificationExecutor<IngredientGroup> {

}

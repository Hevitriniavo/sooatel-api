package com.fresh.coding.sooatelapi.repositories;

import com.fresh.coding.sooatelapi.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}

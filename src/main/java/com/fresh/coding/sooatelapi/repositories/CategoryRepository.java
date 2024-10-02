package com.fresh.coding.sooatelapi.repositories;

import com.fresh.coding.sooatelapi.entities.Category;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Page<Category> findByNameContainingIgnoreCase(String name, Pageable pageable);

    @Modifying
    @Transactional
    @Query("DELETE FROM Menu m WHERE m.category.id = :categoryId")
    int deleteMenusByCategoryId(Long categoryId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Category c WHERE c.id = :categoryId")
    int deleteCategoryById(Long categoryId);

}

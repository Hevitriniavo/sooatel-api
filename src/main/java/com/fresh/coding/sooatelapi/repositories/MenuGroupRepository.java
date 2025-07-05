package com.fresh.coding.sooatelapi.repositories;

import com.fresh.coding.sooatelapi.entities.MenuGroup;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuGroupRepository extends JpaRepository<MenuGroup, Long> {

    Page<MenuGroup> findByNameContainingIgnoreCase(String name, Pageable pageable);

    @Modifying
    @Transactional
    @Query("DELETE FROM Menu m WHERE m.menuGroup.id = :categoryId")
    int deleteMenusByCategoryId(Long categoryId);

    @Modifying
    @Transactional
    @Query("DELETE FROM MenuGroup c WHERE c.id = :categoryId")
    int deleteCategoryById(Long categoryId);

}

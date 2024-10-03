package com.fresh.coding.sooatelapi.repositories;

import com.fresh.coding.sooatelapi.entities.Menu;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Menu m WHERE m.id = :id")
    int deleteMenuById(@Param("id") Long id);
}

package com.fresh.coding.sooatelapi.repositories;

import com.fresh.coding.sooatelapi.entities.Unit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {
    Page<Unit> findByNameContainingIgnoreCase(String name, Pageable pageable);
}

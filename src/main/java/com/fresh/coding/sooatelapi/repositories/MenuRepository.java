package com.fresh.coding.sooatelapi.repositories;

import com.fresh.coding.sooatelapi.entities.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
}
package com.fresh.coding.sooatelapi.repositories;

import com.fresh.coding.sooatelapi.entities.Cash;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CashRepository extends JpaRepository<Cash, Long> {
}

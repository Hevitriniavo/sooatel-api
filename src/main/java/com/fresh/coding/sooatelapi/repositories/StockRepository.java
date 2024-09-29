package com.fresh.coding.sooatelapi.repositories;

import com.fresh.coding.sooatelapi.entities.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
}

package com.fresh.coding.sooatelapi.repositories;

import com.fresh.coding.sooatelapi.entities.CashHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;



@Repository
public interface CashHistoryRepository extends JpaRepository<CashHistory, Long> {
}

package com.fresh.coding.sooatelapi.repositories;

import com.fresh.coding.sooatelapi.entities.CashHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CashHistoryRepository extends JpaRepository<CashHistory, Long> {

    @Query("""
    SELECT ch FROM CashHistory ch WHERE DATE(ch.transactionDate) = :date
""")
    List<CashHistory> findAllByTransactionDate(@Param("date") LocalDate date);


}

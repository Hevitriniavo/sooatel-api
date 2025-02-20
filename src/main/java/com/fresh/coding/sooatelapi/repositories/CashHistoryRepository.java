package com.fresh.coding.sooatelapi.repositories;

import com.fresh.coding.sooatelapi.dtos.PaymentMethodProfitDTO;
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
           SELECT new com.fresh.coding.sooatelapi.dtos.PaymentMethodProfitDTO(
               ch.modeOfTransaction,
               SUM(CASE WHEN ch.transactionType = 'IN' THEN ch.amount ELSE 0 END),
               SUM(CASE WHEN ch.transactionType = 'OUT' THEN ch.amount ELSE 0 END),
               SUM(CASE WHEN ch.transactionType = 'IN' THEN ch.amount ELSE - ch.amount END)
           )
           FROM CashHistory ch
           WHERE DATE(ch.transactionDate) = :date
           GROUP BY ch.modeOfTransaction
           """)
    List<PaymentMethodProfitDTO> findProfitByPaymentMethod(@Param("date") LocalDate date);
}

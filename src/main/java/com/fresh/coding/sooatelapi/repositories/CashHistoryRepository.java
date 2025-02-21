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

    @Query(value = """
               SELECT mode_of_transaction,
                      SUM(CASE WHEN transaction_type IN ('MANUAL_DEPOSIT', 'MENU_SALE_DEPOSIT') THEN amount ELSE 0 END) - 
                      SUM(CASE WHEN transaction_type IN ('INGREDIENT_PURCHASE', 'MANUAL_WITHDRAWAL') THEN amount ELSE 0 END) 
                      AS benefice 
               FROM cash_history 
               WHERE DATE(transaction_date) BETWEEN :startDate AND :endDate 
               GROUP BY mode_of_transaction
               """, nativeQuery = true)
    List<Object[]> getBeneficeByModeOfTransactionAndPeriod(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query(value = """
               SELECT mode_of_transaction,
                      SUM(CASE WHEN transaction_type = 'MENU_SALE_DEPOSIT' THEN amount ELSE 0 END) - 
                      SUM(CASE WHEN transaction_type = 'INGREDIENT_PURCHASE' THEN amount ELSE 0 END) 
                      AS benefice 
               FROM cash_history 
               WHERE DATE(transaction_date) BETWEEN :startDate AND :endDate 
               GROUP BY mode_of_transaction
               """, nativeQuery = true)
    List<Object[]> getBeneficeByModeOfTransactionAndPeriodForMenuSale(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query(value = """
               SELECT
                      SUM(CASE WHEN transaction_type = 'MENU_SALE_DEPOSIT' THEN amount ELSE 0 END) - 
                      SUM(CASE WHEN transaction_type = 'INGREDIENT_PURCHASE' THEN amount ELSE 0 END) 
                      AS benefice 
               FROM cash_history 
               WHERE DATE(transaction_date) BETWEEN :startDate AND :endDate
               """, nativeQuery = true)
    Double getTotalMenuSaleBenefice(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query(value = """
               SELECT
                      SUM(CASE WHEN transaction_type IN ('MANUAL_DEPOSIT', 'MENU_SALE_DEPOSIT') THEN amount ELSE 0 END) - 
                      SUM(CASE WHEN transaction_type IN ('INGREDIENT_PURCHASE', 'MANUAL_WITHDRAWAL') THEN amount ELSE 0 END) 
                      AS benefice 
               FROM cash_history 
               WHERE DATE(transaction_date) BETWEEN :startDate AND :endDate
               """, nativeQuery = true)
    Double getTotalBeneficeByModeOfTransactionAndPeriod(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}

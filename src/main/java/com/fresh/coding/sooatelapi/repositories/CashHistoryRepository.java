package com.fresh.coding.sooatelapi.repositories;

import com.fresh.coding.sooatelapi.dtos.ProfitLossDTO;
import com.fresh.coding.sooatelapi.dtos.payments.PaymentMethodTotalAmountDTO;
import com.fresh.coding.sooatelapi.entities.CashHistory;
import com.fresh.coding.sooatelapi.enums.PaymentMethod;
import com.fresh.coding.sooatelapi.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;



@Repository
public interface CashHistoryRepository extends JpaRepository<CashHistory, Long> {


    @Query("""
           SELECT new com.fresh.coding.sooatelapi.dtos.payments.PaymentMethodTotalAmountDTO(
               c.modeOfTransaction,
               SUM(c.amount)
           )
           FROM CashHistory c
           WHERE c.transactionType = :transactionType
           AND c.modeOfTransaction IN :paymentMethods
           GROUP BY c.modeOfTransaction
           """)
    List<PaymentMethodTotalAmountDTO> getTotalAmountByPaymentMethod(TransactionType transactionType, TransactionType);

    @Query("""
       SELECT new com.fresh.coding.sooatelapi.dtos.ProfitLossDTO(
           SUM(CASE WHEN c.transactionType = CashHistory. THEN c.amount ELSE 0 END), 
           SUM(CASE WHEN c.transactionType = 'OUT' THEN c.amount ELSE 0 END), 
           (SUM(CASE WHEN c.transactionType = 'IN' THEN c.amount ELSE 0 END) - 
            SUM(CASE WHEN c.transactionType = 'OUT' THEN c.amount ELSE 0 END))
       ) 
       FROM CashHistory c
       """)
    ProfitLossDTO getProfitLoss();

}

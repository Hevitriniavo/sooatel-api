package com.fresh.coding.sooatelapi.services.cash;

import com.fresh.coding.sooatelapi.dtos.TransactionProfitDTO;
import com.fresh.coding.sooatelapi.dtos.cash.CashDTO;
import com.fresh.coding.sooatelapi.entities.Cash;

import java.time.LocalDate;
import java.util.List;


public interface CashService {
    void processCashTransaction(CashDTO cashDTO);
    Cash getCurrentCashBalance();

    List<TransactionProfitDTO> getBeneficeByModeOfTransactionAndPeriod(LocalDate startDate, LocalDate endDate);

    List<TransactionProfitDTO> getMenuSaleBeneficeByModeOfTransactionAndPeriod(LocalDate startDate, LocalDate endDate);

    Double getTotalMenuSaleBenefice(LocalDate startDate, LocalDate endDate);

    Double getTotalBeneficeByModeOfTransactionAndPeriod(LocalDate startDate, LocalDate endDate);
}

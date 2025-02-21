package com.fresh.coding.sooatelapi.services.cash;

import com.fresh.coding.sooatelapi.dtos.TransactionProfitDTO;
import com.fresh.coding.sooatelapi.dtos.cash.CashDTO;
import com.fresh.coding.sooatelapi.entities.Cash;

import java.time.LocalDate;
import java.util.List;

public interface CashService {
    void processCashTransaction(CashDTO cashDTO);
    Cash getCurrentCashBalance();
    List<TransactionProfitDTO> getProfitByPaymentMethod(LocalDate date);
}

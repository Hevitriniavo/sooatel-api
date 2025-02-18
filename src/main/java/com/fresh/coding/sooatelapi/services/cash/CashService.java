package com.fresh.coding.sooatelapi.services.cash;

import com.fresh.coding.sooatelapi.dtos.cash.CashDTO;
import com.fresh.coding.sooatelapi.entities.Cash;

public interface CashService {
    void processCashTransaction(CashDTO cashDTO);
    Cash getCurrentCashBalance();
}

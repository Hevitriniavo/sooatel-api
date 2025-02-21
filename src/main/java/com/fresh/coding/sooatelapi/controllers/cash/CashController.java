package com.fresh.coding.sooatelapi.controllers.cash;

import com.fresh.coding.sooatelapi.dtos.TransactionProfitDTO;
import com.fresh.coding.sooatelapi.dtos.cash.CashDTO;
import com.fresh.coding.sooatelapi.entities.Cash;
import com.fresh.coding.sooatelapi.enums.TransactionType;
import com.fresh.coding.sooatelapi.services.cash.CashService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@Validated
@RequestMapping
@RequiredArgsConstructor
public class CashController {

    private final CashService cashService;

    @PostMapping("/cash/transaction")
    public ResponseEntity<String> processCashTransaction(@RequestBody @Valid CashDTO cashDTO) {
        cashService.processCashTransaction(cashDTO);
        return ResponseEntity.ok("Transaction processed successfully.");
    }

    @GetMapping("/cash/balance")
    public Cash getCurrentCashBalance() {
        return cashService.getCurrentCashBalance();
    }



    @GetMapping("/cash/status")
    public List<TransactionType> get() {
        return List.of(TransactionType.MANUAL_DEPOSIT, TransactionType.MANUAL_WITHDRAWAL);
    }

    @GetMapping("/profits")
    public List<TransactionProfitDTO> getProfits(
            @RequestParam(required = false) LocalDate date
    ) {
        return cashService.getProfitByPaymentMethod(date);
    }


}

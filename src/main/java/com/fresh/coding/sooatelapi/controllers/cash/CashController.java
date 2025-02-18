package com.fresh.coding.sooatelapi.controllers.cash;

import com.fresh.coding.sooatelapi.dtos.cash.CashDTO;
import com.fresh.coding.sooatelapi.entities.Cash;
import com.fresh.coding.sooatelapi.services.cash.CashService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/cash")
@RequiredArgsConstructor
public class CashController {

    private final CashService cashService;

    @PostMapping("/transaction")
    public ResponseEntity<String> processCashTransaction(@RequestBody @Valid CashDTO cashDTO) {
        cashService.processCashTransaction(cashDTO);
        return ResponseEntity.ok("Transaction processed successfully.");
    }

    @GetMapping("/balance")
    public Cash getCurrentCashBalance() {
        return cashService.getCurrentCashBalance();
    }

}

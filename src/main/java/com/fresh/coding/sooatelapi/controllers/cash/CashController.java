package com.fresh.coding.sooatelapi.controllers.cash;

import com.fresh.coding.sooatelapi.dtos.TransactionProfitDTO;
import com.fresh.coding.sooatelapi.dtos.cash.CashDTO;
import com.fresh.coding.sooatelapi.entities.Cash;
import com.fresh.coding.sooatelapi.entities.CashHistory;
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

    @GetMapping("/histories/{id}")
    public CashHistory getHistory(@PathVariable Long id) {
        return cashService.getHistory(id);
    }

    @GetMapping("/histories")
    public List<CashHistory> getHistories() {
        return cashService.getHistories();
    }





    @GetMapping("/cash/status")
    public List<TransactionType> get() {
        return List.of(TransactionType.MANUAL_DEPOSIT, TransactionType.MANUAL_WITHDRAWAL);
    }

    @GetMapping("/benefice")
    public List<TransactionProfitDTO> getBenefice(
            @RequestParam(value = "startDate", required = false) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) LocalDate endDate) {
        return cashService.getBeneficeByModeOfTransactionAndPeriod(startDate, endDate);
    }

    @GetMapping("/menuSaleBenefice")
    public List<TransactionProfitDTO> getMenuSaleBenefice(
            @RequestParam(value = "startDate", required = false) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) LocalDate endDate) {
        return cashService.getMenuSaleBeneficeByModeOfTransactionAndPeriod(startDate, endDate);
    }

    @GetMapping("/totalMenuSaleBenefice")
    public Double getTotalMenuSaleBenefice(
            @RequestParam(value = "startDate", required = false) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) LocalDate endDate
    ) {
        return cashService.getTotalMenuSaleBenefice(startDate, endDate);
    }

    @GetMapping("/totalBenefice")
    public Double getTotalBenefice(
            @RequestParam(value = "startDate", required = false) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) LocalDate endDate) {
        return cashService.getTotalBeneficeByModeOfTransactionAndPeriod(startDate, endDate);
    }
}

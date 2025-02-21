package com.fresh.coding.sooatelapi.services.cash;

import com.fresh.coding.sooatelapi.dtos.TransactionProfitDTO;
import com.fresh.coding.sooatelapi.dtos.cash.CashDTO;
import com.fresh.coding.sooatelapi.entities.Cash;
import com.fresh.coding.sooatelapi.entities.CashHistory;
import com.fresh.coding.sooatelapi.enums.PaymentMethod;
import com.fresh.coding.sooatelapi.exceptions.HttpBadRequestException;
import com.fresh.coding.sooatelapi.exceptions.HttpNotFoundException;
import com.fresh.coding.sooatelapi.repositories.RepositoryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CashServiceImpl implements CashService {
    private final RepositoryFactory factory;


    @Override
    @Transactional
    public void processCashTransaction(CashDTO cashDTO) {
        var cashRepo = this.factory.getCashRepository();
        var cashHistoryRepo = this.factory.getCashHistoryRepository();
        var cash = this.findOrCreateCash();

        var cashHistory = CashHistory.builder()
                .amount(cashDTO.getAmount())
                .transactionType(cashDTO.getTransactionType())
                .modeOfTransaction(cashDTO.getModeOfTransaction())
                .description(cashDTO.getDescription())
                .build();

        var type = cashHistory.getTransactionType();
        var balance = cash.getBalance();

        switch (type) {
            case MANUAL_DEPOSIT:
            case MENU_SALE_DEPOSIT:
                cash.setBalance(balance + cashDTO.getAmount());
                break;

            case MANUAL_WITHDRAWAL:
                if (balance < cashDTO.getAmount()) {
                    throw new HttpBadRequestException("Fonds insuffisants dans la caisse pour ce retrait.");
                }
                cash.setBalance(balance - cashDTO.getAmount());
                break;

            case INGREDIENT_PURCHASE:
                if (balance < cashDTO.getAmount()) {
                    throw new HttpBadRequestException("Fonds insuffisants pour cet achat d'ingrédients.");
                }
                cash.setBalance(balance - cashDTO.getAmount());
                break;

            default:
                throw new HttpBadRequestException("Type de transaction inconnu.");
        }

        cash.setLastUpdated(LocalDateTime.now());
        cashRepo.save(cash);

        cashRepo.save(cash);

        cashHistoryRepo.save(cashHistory);
    }

    @Override
    public Cash getCurrentCashBalance() {
        return this.findOrCreateCash();
    }

    @Override
    public List<TransactionProfitDTO> getBeneficeByModeOfTransactionAndPeriod(LocalDate startDate, LocalDate endDate) {
        LocalDate beginDate = (startDate != null) ? startDate : LocalDate.now();
        LocalDate finalEndDate = (endDate != null) ? endDate : LocalDate.now();
        var cashHistoryRepo = this.factory.getCashHistoryRepository();
        var profitsInDB = cashHistoryRepo.getBeneficeByModeOfTransactionAndPeriod(beginDate, finalEndDate);

        return profitsInDB.stream()
                .map(row -> new TransactionProfitDTO(
                        PaymentMethod.valueOf(row[0].toString()),
                        Double.valueOf(row[1].toString())))
                .collect(Collectors.toList());
    }


    @Override
    public List<TransactionProfitDTO> getMenuSaleBeneficeByModeOfTransactionAndPeriod(LocalDate startDate, LocalDate endDate) {
        LocalDate beginDate = (startDate != null) ? startDate : LocalDate.now();
        LocalDate finalEndDate = (endDate != null) ? endDate : LocalDate.now();
        var cashHistoryRepo = this.factory.getCashHistoryRepository();
        var profitsInDB = cashHistoryRepo.getBeneficeByModeOfTransactionAndPeriodForMenuSale(beginDate, finalEndDate);

        return profitsInDB.stream()
                .map(row -> new TransactionProfitDTO(
                        PaymentMethod.valueOf(row[0].toString()),
                        Double.valueOf(row[1].toString())))
                .collect(Collectors.toList());
    }


    @Override
    public Double getTotalMenuSaleBenefice(LocalDate startDate, LocalDate endDate) {
        LocalDate beginDate = (startDate != null) ? startDate : LocalDate.now();
        LocalDate finalEndDate = (endDate != null) ? endDate : LocalDate.now();
        var cashHistoryRepo = this.factory.getCashHistoryRepository();
        var totalBenefice = cashHistoryRepo.getTotalMenuSaleBenefice(beginDate, finalEndDate);
        return totalBenefice != null ? totalBenefice : 0.0;
    }


    @Override
    public Double getTotalBeneficeByModeOfTransactionAndPeriod(LocalDate startDate, LocalDate endDate) {
        LocalDate beginDate = (startDate != null) ? startDate : LocalDate.now();
        LocalDate finalEndDate = (endDate != null) ? endDate : LocalDate.now();
        var cashHistoryRepo = this.factory.getCashHistoryRepository();
        var totalBenefice = cashHistoryRepo.getTotalBeneficeByModeOfTransactionAndPeriod(beginDate, finalEndDate);
        return totalBenefice != null ? totalBenefice : 0.0;
    }

    @Override
    public List<CashHistory> getHistories() {
        var cashHistoryRepo = this.factory.getCashHistoryRepository();
        return cashHistoryRepo.findAll();
    }

    @Override
    public CashHistory getHistory(Long id) {
        var cashHistoryRepo = this.factory.getCashHistoryRepository();
        return cashHistoryRepo.findById(id)
                .orElseThrow(() -> new HttpNotFoundException( "Historique de caisse non trouvé avec l'ID : " + id));
    }

    private Cash findOrCreateCash() {
        var cashRepo = this.factory.getCashRepository();
        var cash = Cash.builder().balance(0.0).lastUpdated(LocalDateTime.now()).build();
        return cashRepo.findAll().isEmpty() ? cashRepo.save(cash) : cashRepo.findAll().getFirst();
    }

}

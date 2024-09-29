package com.fresh.coding.sooatelapi.services.stocks.impl;

import com.fresh.coding.sooatelapi.dtos.StockPurchaseDto;
import com.fresh.coding.sooatelapi.entities.Operation;
import com.fresh.coding.sooatelapi.entities.Purchase;
import com.fresh.coding.sooatelapi.enums.OperationType;
import com.fresh.coding.sooatelapi.exceptions.HttpNotFoundException;
import com.fresh.coding.sooatelapi.repositories.RepositoryFactory;
import com.fresh.coding.sooatelapi.services.stocks.StockAndCreatePurchase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class StockAndCreatePurchaseImpl implements StockAndCreatePurchase {
    private final RepositoryFactory factory;

    @Override
    @Transactional
    public void add(StockPurchaseDto stockPurchaseDto) {
        var ingredientRepository = factory.getIngredientRepository();
        var stockRepository = factory.getStockRepository();
        var purchaseRepository = factory.getPurchaseRepository();
        var operationRepository = factory.getOperationRepository();

        var ingredient = ingredientRepository.findById(stockPurchaseDto.getIngredientId())
                .orElseThrow(() -> new HttpNotFoundException("Ingredient not found"));

        var stock = stockRepository.findByIngredient(ingredient)
                .orElseThrow(() -> new HttpNotFoundException("Stock not found"));

        stock.setIngredient(ingredient);
        stock.setQuantity(stock.getQuantity() + stockPurchaseDto.getQuantity());
        stockRepository.save(stock);

        var purchase = Purchase.builder()
                .ingredient(ingredient)
                .quantity(stockPurchaseDto.getQuantity())
                .cost(stockPurchaseDto.getCost())
                .description(stockPurchaseDto.getDescription())
                .build();
        purchaseRepository.save(purchase);

        var operation = Operation.builder()
                .stock(stock)
                .type(OperationType.ENTRY)
                .date(LocalDateTime.now())
                .description("Achat de " + stockPurchaseDto.getQuantity() + " de " + ingredient.getName() + " le " + LocalDateTime.now())
                .build();
        operationRepository.save(operation);
    }
}

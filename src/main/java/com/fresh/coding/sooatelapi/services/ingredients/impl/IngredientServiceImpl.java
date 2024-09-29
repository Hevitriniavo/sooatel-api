package com.fresh.coding.sooatelapi.services.ingredients.impl;

import com.fresh.coding.sooatelapi.dtos.ingredients.CreateIngredient;
import com.fresh.coding.sooatelapi.dtos.ingredients.IngredientSummarized;
import com.fresh.coding.sooatelapi.entities.Ingredient;
import com.fresh.coding.sooatelapi.entities.Operation;
import com.fresh.coding.sooatelapi.entities.Stock;
import com.fresh.coding.sooatelapi.enums.OperationType;
import com.fresh.coding.sooatelapi.exceptions.HttpNotFoundException;
import com.fresh.coding.sooatelapi.repositories.RepositoryFactory;
import com.fresh.coding.sooatelapi.services.ingredients.IngredientService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {
    private final RepositoryFactory factory;

    @Transactional
    @Override
    public IngredientSummarized create(@NonNull CreateIngredient toCreate) {
        var unitRepository = factory.getUnitRepository();

        var unit = unitRepository.findById(toCreate.getUnitId())
                .orElseThrow(() -> new HttpNotFoundException("Unit not found"));

        var ingredient = new Ingredient();
        BeanUtils.copyProperties(toCreate, ingredient);
        ingredient.setUnit(unit);
        var ingredientRepository = factory.getIngredientRepository();
        var savedIngredient = ingredientRepository.save(ingredient);

        var stock = createStockForIngredient(savedIngredient);
        factory.getStockRepository().save(stock);

        var operation = createInitialOperation(stock);
        factory.getOperationRepository().save(operation);

        return this.toIngredientSummarized(savedIngredient);
    }

    private IngredientSummarized toIngredientSummarized(Ingredient ingredient){
        return new IngredientSummarized(
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getCreatedAt(),
                ingredient.getUpdatedAt()
        );
    }

    private Stock createStockForIngredient(Ingredient ingredient) {
        var stock = new Stock();
        stock.setIngredient(ingredient);
        stock.setQuantity(0.0);
        return stock;
    }

    private Operation createInitialOperation(Stock stock) {
        return Operation.builder()
                .stock(stock)
                .type(OperationType.INITIAL)
                .date(LocalDateTime.now())
                .description("Initial stock for ingredient ID " + stock.getIngredient().getId())
                .build();
    }

}

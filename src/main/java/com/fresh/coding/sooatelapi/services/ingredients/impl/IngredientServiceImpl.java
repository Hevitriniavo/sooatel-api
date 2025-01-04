package com.fresh.coding.sooatelapi.services.ingredients.impl;

import com.fresh.coding.sooatelapi.dtos.ingredients.CreateIngredient;
import com.fresh.coding.sooatelapi.dtos.ingredients.IngredientSummarized;
import com.fresh.coding.sooatelapi.dtos.ingredients.IngredientSummarizedWithUnitName;
import com.fresh.coding.sooatelapi.dtos.ingredients.UpdateIngredient;
import com.fresh.coding.sooatelapi.entities.Ingredient;
import com.fresh.coding.sooatelapi.entities.Operation;
import com.fresh.coding.sooatelapi.entities.Stock;
import com.fresh.coding.sooatelapi.enums.OperationType;
import com.fresh.coding.sooatelapi.exceptions.HttpNotFoundException;
import com.fresh.coding.sooatelapi.repositories.RepositoryFactory;
import com.fresh.coding.sooatelapi.services.ingredients.IngredientService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
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

    @Override
    @Transactional
    public IngredientSummarized update(@NonNull UpdateIngredient toUpdate) {
        var ingredientRepository = factory.getIngredientRepository();
        var unitRepository = factory.getUnitRepository();

        var ingredient = ingredientRepository.findById(toUpdate.getId())
                .orElseThrow(() -> new HttpNotFoundException("Ingredient not found"));

        var unit = unitRepository.findById(toUpdate.getUnitId())
                .orElseThrow(() -> new HttpNotFoundException("Unit not found"));

        ingredient.setName(toUpdate.getName());
        ingredient.setUnit(unit);

        var updatedIngredient = ingredientRepository.save(ingredient);

        return this.toIngredientSummarized(updatedIngredient);
    }

    @Override
    public List<IngredientSummarizedWithUnitName> findAllIngredients() {
        var ingredientRepository = factory.getIngredientRepository();
        var ingredients = ingredientRepository.findAll();

        return ingredients.stream()
                .map(this::toIngredientSummarizedWithUnitName)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        var ingredientRepository = factory.getIngredientRepository();
        var ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new HttpNotFoundException("Ingredient not found"));

        ingredientRepository.delete(ingredient);
    }


    private IngredientSummarized toIngredientSummarized(Ingredient ingredient){
        return new IngredientSummarized(
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getCreatedAt(),
                ingredient.getUpdatedAt()
        );
    }

    private IngredientSummarizedWithUnitName toIngredientSummarizedWithUnitName(Ingredient ingredient) {
        return new IngredientSummarizedWithUnitName(
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getCreatedAt(),
                ingredient.getUpdatedAt(),
                ingredient.getUnit() != null ? ingredient.getUnit().getId() : null,
                ingredient.getUnit() != null ? ingredient.getUnit().getName() : null
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
                .quantity(00.0)
                .date(LocalDateTime.now())
                .description("État initial du stock pour l'ingrédient : " + stock.getIngredient().getName() + " (ID : " + stock.getIngredient().getId() + ")")
                .build();
    }

}

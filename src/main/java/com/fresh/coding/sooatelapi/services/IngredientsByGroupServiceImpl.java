package com.fresh.coding.sooatelapi.services;

import com.fresh.coding.sooatelapi.dtos.categories.ingredients.GroupSummarized;
import com.fresh.coding.sooatelapi.dtos.categories.ingredients.IngredientWithStockSummarized;
import com.fresh.coding.sooatelapi.dtos.categories.ingredients.StockIngredientWithGroup;
import com.fresh.coding.sooatelapi.dtos.unit.UnitSummarized;
import com.fresh.coding.sooatelapi.entities.IngredientGroup;
import com.fresh.coding.sooatelapi.repositories.RepositoryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IngredientsByGroupServiceImpl implements IngredientsByGroupService{
    private final RepositoryFactory repositoryFactory;


    @Override
    public List<GroupSummarized> findStocksByIngredientsGroup() {
        var ingredientGroupRepo = this.repositoryFactory.getIngredientGroupRepository();
        var allGroups = ingredientGroupRepo.getStocksByIngredientsGroup();
        return allGroups.stream().map(this::mapToGroup)
                .collect(Collectors.toList());
    }


    public GroupSummarized mapToGroup(IngredientGroup ingredientGroup) {
        if (ingredientGroup == null) {
            return null;
        }

        GroupSummarized.GroupSummarizedBuilder groupSummarizedBuilder = GroupSummarized.builder()
                .id(ingredientGroup.getId())
                .name(ingredientGroup.getName())
                .createdAt(ingredientGroup.getCreatedAt())
                .updatedAt(ingredientGroup.getUpdatedAt());

        if (ingredientGroup.getIngredients() != null) {
            List<IngredientWithStockSummarized> ingredientsSummary = ingredientGroup.getIngredients().stream()
                    .map(ingredient -> {
                        if (ingredient == null) {
                            return null;
                        }

                        IngredientWithStockSummarized.IngredientWithStockSummarizedBuilder ingredientSummaryBuilder =
                                IngredientWithStockSummarized.builder()
                                        .id(ingredient.getId())
                                        .name(ingredient.getName())
                                        .createdAt(ingredient.getCreatedAt())
                                        .updatedAt(ingredient.getUpdatedAt());

                        if (ingredient.getStock() != null) {
                            StockIngredientWithGroup stockSummary = StockIngredientWithGroup.builder()
                                    .id(ingredient.getStock().getId())
                                    .quantity(ingredient.getStock().getQuantity())
                                    .createdAt(ingredient.getStock().getCreatedAt())
                                    .updatedAt(ingredient.getStock().getUpdatedAt())
                                    .build();
                            ingredientSummaryBuilder.stock(stockSummary);
                        }

                        if (ingredient.getUnit() != null) {
                            UnitSummarized unitSummary = new UnitSummarized(
                                    ingredient.getUnit().getId(),
                                    ingredient.getUnit().getName(),
                                    ingredient.getUnit().getAbbreviation(),
                                    ingredient.getUnit().getCreatedAt(),
                                    ingredient.getUnit().getUpdatedAt()
                            );
                            ingredientSummaryBuilder.unit(unitSummary);
                        }

                        return ingredientSummaryBuilder.build();
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            groupSummarizedBuilder.ingredients(ingredientsSummary);
        }

        return groupSummarizedBuilder.build();
    }

}

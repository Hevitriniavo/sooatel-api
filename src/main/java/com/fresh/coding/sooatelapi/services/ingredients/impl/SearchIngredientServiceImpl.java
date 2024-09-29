package com.fresh.coding.sooatelapi.services.ingredients.impl;

import com.fresh.coding.sooatelapi.dtos.ingredients.IngredientSummarizedWithUnitName;
import com.fresh.coding.sooatelapi.dtos.pagination.PageInfo;
import com.fresh.coding.sooatelapi.dtos.pagination.Paginate;
import com.fresh.coding.sooatelapi.entities.Ingredient;
import com.fresh.coding.sooatelapi.entities.Unit;
import com.fresh.coding.sooatelapi.services.ingredients.SearchIngredientService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchIngredientServiceImpl implements SearchIngredientService {

    private final EntityManager entityManager;

    @Override
    public Paginate<List<IngredientSummarizedWithUnitName>> findAllIngredient(String ingredientName, String unitName, int page, int size) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Ingredient> criteriaQuery = criteriaBuilder.createQuery(Ingredient.class);
        Root<Ingredient> ingredientRoot = criteriaQuery.from(Ingredient.class);
        Join<Ingredient, Unit> unitJoin = ingredientRoot.join("unit");

        Predicate predicate = createPredicate(criteriaBuilder, ingredientRoot, unitJoin, ingredientName, unitName);
        criteriaQuery.select(ingredientRoot).where(predicate);

        List<Ingredient> ingredients = entityManager.createQuery(criteriaQuery)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList();

        List<IngredientSummarizedWithUnitName> summarizedIngredients = summarizeIngredients(ingredients);

        long totalItems = getTotalCount(ingredientName, unitName);
        int totalPages = (int) Math.ceil((double) totalItems / size);

        return new Paginate<>(summarizedIngredients, createPageInfo(page, totalPages, totalItems));
    }

    private Predicate createPredicate(CriteriaBuilder criteriaBuilder, Root<Ingredient> ingredientRoot, Join<Ingredient, Unit> unitJoin, String ingredientName, String unitName) {
        Predicate predicate = criteriaBuilder.conjunction();

        if (isNotEmpty(ingredientName)) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.like(criteriaBuilder.lower(ingredientRoot.get("name")), "%" + ingredientName.toLowerCase() + "%"));
        }

        if (isNotEmpty(unitName)) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.like(criteriaBuilder.lower(unitJoin.get("name")), "%" + unitName.toLowerCase() + "%"));
        }

        return predicate;
    }

    private boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    private List<IngredientSummarizedWithUnitName> summarizeIngredients(List<Ingredient> ingredients) {
        return ingredients.stream()
                .map(ingredient -> new IngredientSummarizedWithUnitName(
                        ingredient.getId(),
                        ingredient.getName(),
                        ingredient.getCreatedAt(),
                        ingredient.getUpdatedAt(),
                        ingredient.getUnit() != null ? ingredient.getUnit().getName() : null))
                .collect(Collectors.toList());
    }

    private long getTotalCount(String ingredientName, String unitName) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Ingredient> countRoot = countQuery.from(Ingredient.class);
        Join<Ingredient, Unit> unitJoin = countRoot.join("unit", JoinType.LEFT);

        Predicate predicate = createPredicate(criteriaBuilder, countRoot, unitJoin, ingredientName, unitName);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);

        return entityManager.createQuery(countQuery).getSingleResult();
    }

    private PageInfo createPageInfo(int page, int totalPages, long totalItems) {
        return new PageInfo(
                (page + 1) < totalPages,
                page > 0,
                totalPages,
                page + 1,
                (int) totalItems
        );
    }
}

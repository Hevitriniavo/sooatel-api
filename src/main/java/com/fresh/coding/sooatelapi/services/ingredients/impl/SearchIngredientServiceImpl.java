package com.fresh.coding.sooatelapi.services.ingredients.impl;

import com.fresh.coding.sooatelapi.dtos.ingredients.IngredientSummarizedWithUnitAndGroup;
import com.fresh.coding.sooatelapi.dtos.pagination.PageInfo;
import com.fresh.coding.sooatelapi.dtos.pagination.Paginate;
import com.fresh.coding.sooatelapi.dtos.searchs.IngredientSearch;
import com.fresh.coding.sooatelapi.entities.Ingredient;
import com.fresh.coding.sooatelapi.entities.Unit;
import com.fresh.coding.sooatelapi.services.ingredients.SearchIngredientService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchIngredientServiceImpl implements SearchIngredientService {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Paginate<List<IngredientSummarizedWithUnitAndGroup>> findAllIngredient(IngredientSearch ingredientSearch, int page, int size) {
        var builder = em.getCriteriaBuilder();
        var query = builder.createQuery(Ingredient.class);
        var root = query.from(Ingredient.class);
        Join<Ingredient, Unit> unitJoin = root.join("unit", JoinType.LEFT);
        var predicates = this.builderPredicates(ingredientSearch, builder, root, unitJoin);
        query.where(predicates.toArray(new Predicate[0]));
        var typedQuery = em.createQuery(query.select(root));
        typedQuery.setFirstResult(page * size);
        typedQuery.setMaxResults(size);
        var products = typedQuery.getResultList();
        var totalItems = this.getTotalCount(ingredientSearch);
        var totalPages = (int) Math.ceil((double) totalItems / size);

        var hasNext = page < totalPages - 1;
        var hasPrevious = page > 0;

        return new Paginate<>(toSummarized(products), new PageInfo(
                hasNext,
                hasPrevious,
                totalPages,
                page,
                (int) totalItems
        ));
    }

    private List<IngredientSummarizedWithUnitAndGroup> toSummarized(List<Ingredient> products) {
        return products.stream().map(product -> new IngredientSummarizedWithUnitAndGroup(
                product.getId(),
                product.getName(),
                product.getCreatedAt(),
                product.getUpdatedAt(),
                product.getUnit() != null ? product.getUnit().getId() : null,
                product.getUnit() != null ? product.getUnit().getName() : null,
                product.getGroup() != null ? product.getGroup().getId() : null,
                product.getGroup() != null ? product.getGroup().getName() : null
                )
        ).collect(Collectors.toList());
    }

    private long getTotalCount(IngredientSearch ingredientSearch) {
        var builder = em.getCriteriaBuilder();
        var query = builder.createQuery(Long.class);
        var root = query.from(Ingredient.class);
        Join<Ingredient, Unit> unitJoin = root.join("unit", JoinType.LEFT);
        var predicates = this.builderPredicates(ingredientSearch, builder, root, unitJoin);
        query.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
        return em.createQuery(query).getSingleResult();
    }

    private List<Predicate> builderPredicates(IngredientSearch ingredientSearch, CriteriaBuilder builder, Root<Ingredient> root, Join<Ingredient, Unit> unitJoin) {
        var predicates = new ArrayList<Predicate>();

        if (ingredientSearch.getIngredientName() != null && !ingredientSearch.getIngredientName().isBlank()) {
            predicates.add(builder.like(
                    builder.lower(root.get("name")),
                    "%" + ingredientSearch.getIngredientName().toLowerCase() + "%"
            ));
        }

        if (ingredientSearch.getUnitName() != null && !ingredientSearch.getUnitName().isBlank()) {
            predicates.add(builder.like(
                    builder.lower(unitJoin.get("name")),
                    "%" + ingredientSearch.getUnitName().toLowerCase() + "%"
            ));
        }
        return predicates;
    }
}

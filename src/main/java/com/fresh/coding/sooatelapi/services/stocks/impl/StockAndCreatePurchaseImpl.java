package com.fresh.coding.sooatelapi.services.stocks.impl;

import com.fresh.coding.sooatelapi.dtos.StockPurchaseDto;
import com.fresh.coding.sooatelapi.dtos.pagination.PageInfo;
import com.fresh.coding.sooatelapi.dtos.pagination.Paginate;
import com.fresh.coding.sooatelapi.dtos.searchs.StockSearch;
import com.fresh.coding.sooatelapi.dtos.stocks.StockSummarized;
import com.fresh.coding.sooatelapi.entities.Ingredient;
import com.fresh.coding.sooatelapi.entities.Operation;
import com.fresh.coding.sooatelapi.entities.Purchase;
import com.fresh.coding.sooatelapi.entities.Stock;
import com.fresh.coding.sooatelapi.enums.OperationType;
import com.fresh.coding.sooatelapi.exceptions.HttpNotFoundException;
import com.fresh.coding.sooatelapi.repositories.RepositoryFactory;
import com.fresh.coding.sooatelapi.services.stocks.StockAndCreatePurchase;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockAndCreatePurchaseImpl implements StockAndCreatePurchase {
    private final RepositoryFactory factory;

    @PersistenceContext
    private EntityManager em;

    public StockAndCreatePurchaseImpl(RepositoryFactory factory) {
        this.factory = factory;
    }

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

    @Override
    public Paginate<List<StockSummarized>> findAllStock(StockSearch stockSearch, int page, int size) {
        var builder = em.getCriteriaBuilder();
        var query = builder.createQuery(Stock.class);
        var root = query.from(Stock.class);
        Join<Stock, Ingredient> ingredientJoin = root.join("ingredient", JoinType.LEFT);
        var predicates = buildPredicates(stockSearch, builder, root, ingredientJoin);

        query.where(predicates.toArray(new Predicate[0]));

        var typedQuery = em.createQuery(query.select(root));
        typedQuery.setFirstResult(page * size);
        typedQuery.setMaxResults(size);

        var stocks = typedQuery.getResultList();

        var totalItems = getTotalCount(stockSearch);
        var totalPages = (int) Math.ceil((double) totalItems / size);

        var hasNext = page < totalPages - 1;
        var hasPrevious = page > 0;

        return new Paginate<>(this.toSummarized(stocks), new PageInfo(
                hasNext,
                hasPrevious,
                totalPages,
                page,
                (int) totalItems
        ));
    }

    private List<StockSummarized> toSummarized(List<Stock> stocks) {
        return stocks.stream().map(stock -> new StockSummarized(
                stock.getId(),
                stock.getQuantity(),
                stock.getIngredient() != null ? stock.getIngredient().getName() : null,
                stock.getIngredient() != null ? stock.getIngredient().getId() : null,
                stock.getCreatedAt(),
                stock.getUpdatedAt()
        )).collect(Collectors.toList());
    }


    public long getTotalCount(StockSearch stockSearch) {
        var builder = em.getCriteriaBuilder();
        var query = builder.createQuery(Long.class);
        var root = query.from(Stock.class);
        Join<Stock, Ingredient> ingredientJoin = root.join("ingredient", JoinType.LEFT);
        var predicates = buildPredicates(stockSearch, builder, root, ingredientJoin);
        query.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));
        return em.createQuery(query).getSingleResult();
    }

    private List<Predicate> buildPredicates(StockSearch stockSearch, CriteriaBuilder builder, Root<Stock> root, Join<Stock, Ingredient> ingredientJoin) {
        var predicates = new ArrayList<Predicate>();

        if (stockSearch.getIngredientName() != null && !stockSearch.getIngredientName().isEmpty() && !stockSearch.getIngredientName().isBlank()) {
            predicates.add(builder.like(
                    builder.lower(ingredientJoin.get("name")),
                    "%" + stockSearch.getIngredientName().toLowerCase() + "%"
            ));
        }

        if (stockSearch.getQuantityMin() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("quantity"), stockSearch.getQuantityMin()));
        }

        if (stockSearch.getQuantityMax() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("quantity"), stockSearch.getQuantityMax()));
        }

        if (stockSearch.getStartDate() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("updatedAt"), stockSearch.getStartDate()));
        }

        if (stockSearch.getStartEnd() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("updatedAt"), stockSearch.getStartEnd()));
        }
        return predicates;
    }


}

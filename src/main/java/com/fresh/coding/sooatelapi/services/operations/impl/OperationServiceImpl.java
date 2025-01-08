package com.fresh.coding.sooatelapi.services.operations.impl;

import com.fresh.coding.sooatelapi.dtos.operations.OperationSummarized;
import com.fresh.coding.sooatelapi.dtos.operations.OperationWithStock;
import com.fresh.coding.sooatelapi.dtos.pagination.PageInfo;
import com.fresh.coding.sooatelapi.dtos.pagination.Paginate;
import com.fresh.coding.sooatelapi.dtos.searchs.OperationSearch;
import com.fresh.coding.sooatelapi.dtos.searchs.TotalStockQuery;
import com.fresh.coding.sooatelapi.dtos.statistc.TotalStock;
import com.fresh.coding.sooatelapi.entities.Ingredient;
import com.fresh.coding.sooatelapi.entities.Operation;
import com.fresh.coding.sooatelapi.entities.Stock;
import com.fresh.coding.sooatelapi.enums.OperationType;
import com.fresh.coding.sooatelapi.repositories.RepositoryFactory;
import com.fresh.coding.sooatelapi.services.operations.OperationService;
import com.fresh.coding.sooatelapi.services.specifications.OperationSpec;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class OperationServiceImpl implements OperationService {

    private final RepositoryFactory repositoryFactory;
    private final EntityManager entityManager;

    @Override
    public Paginate<List<OperationSummarized>> findAllOperations(OperationSearch search, int page, int size) {
        var spec = Specification.where(OperationSpec.filter(search));
        var pageable = PageRequest.of(page , size);
        var operationsPage = repositoryFactory.getOperationRepository().findAll(spec, pageable);
        var summarizedPage = operationsPage.map(this::mapToSummarized);

        var pageInfo = new PageInfo(
                operationsPage.hasNext(),
                operationsPage.hasPrevious(),
                operationsPage.getTotalPages(),
                operationsPage.getNumber(),
                (int) operationsPage.getTotalElements()
        );

        return new Paginate<>(summarizedPage.getContent(), pageInfo);
    }

    @Override
    public OperationWithStock findOperationDetailByStockId(Long stockId) {
        var res = new OperationWithStock();
        var operations = repositoryFactory.getOperationRepository().findOperationsByStockId(stockId);
        res.setStockId(operations.getFirst().getStock().getId());
        res.setIngredientId(operations.getFirst().getStock().getIngredient().getId());
        res.setIngredientName(operations.getFirst().getStock().getIngredient().getName());
        var resOperations = res.getOperations();
        operations.forEach(operation -> resOperations.add(
                mapToSummarized(operation)
        ));
     return res;
    }

    @Override
    public List<TotalStock> getTotalStocks(TotalStockQuery query) {
        if (query.getDate() == null) {
            query.setDate(LocalDate.now());
        }

        var cb = entityManager.getCriteriaBuilder();
        var cq = cb.createQuery(Tuple.class);

        var stockRoot = cq.from(Stock.class);

        var ingredientJoin = stockRoot.join("ingredient");
        var operationJoin = stockRoot.join("operations", JoinType.LEFT);
        var unitJoin = ingredientJoin.join("unit", JoinType.LEFT);

        cq.multiselect(
                ingredientJoin.get("name").alias("ingredientName"),
                unitJoin.get("abbreviation").alias("unitAbbreviation"),

                cb.sum(
                        cb.<Double>selectCase()
                                .when(cb.equal(operationJoin.get("type"), OperationType.SORTIE), cb.prod(operationJoin.get("quantity"), -1.0))
                                .otherwise(operationJoin.get("quantity"))
                ).alias("totalQuantity")
        );

        var predicates = new ArrayList<Predicate>();
        predicates.add(cb.lessThanOrEqualTo(operationJoin.get("date"), query.getDateAsLocalDateTime()));
        if (query.getIngredientId() != null) {
            predicates.add(cb.equal(ingredientJoin.get("id"), query.getIngredientId()));
        }
        cq.where(predicates.toArray(new Predicate[0]));

        var havingPredicates = new ArrayList<Predicate>();
        Expression<Double> sumExpression = cb.sum(
                cb.<Double>selectCase()
                        .when(cb.equal(operationJoin.get("type"), OperationType.SORTIE), cb.prod(operationJoin.get("quantity"), -1.0))
                        .otherwise(operationJoin.get("quantity"))
        );

        if (query.getMinTotalQuantity() != null) {
            havingPredicates.add(cb.ge(sumExpression, query.getMinTotalQuantity()));
        }

        if (query.getMaxTotalQuantity() != null) {
            havingPredicates.add(cb.le(sumExpression, query.getMaxTotalQuantity()));
        }

        if (!havingPredicates.isEmpty()) {
            cq.having(havingPredicates.toArray(new Predicate[0]));
        }

        cq.groupBy(ingredientJoin.get("name"), unitJoin.get("abbreviation"));


        List<Tuple> results = entityManager.createQuery(cq).getResultList();

        List<TotalStock> totalStocks = new ArrayList<>();
        for (Tuple result : results) {
            TotalStock totalStock = new TotalStock();
            totalStock.setIngredientName(result.get("ingredientName", String.class));
            totalStock.setTotalQuantity(result.get("totalQuantity", Double.class));
            totalStock.setUnitAbbreviation(result.get("unitAbbreviation", String.class));
            totalStocks.add(totalStock);
        }

        return totalStocks;
    }


    private OperationSummarized mapToSummarized(Operation operation) {
        return OperationSummarized.builder()
                .id(operation.getId())
                .type(operation.getType())
                .quantity(operation.getQuantity())
                .stockId(operation.getStock() != null ? operation.getStock().getId(): null)
                .date(operation.getDate())
                .description(operation.getDescription())
                .build();
    }
}

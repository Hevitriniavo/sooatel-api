package com.fresh.coding.sooatelapi.services.operations.impl;

import com.fresh.coding.sooatelapi.dtos.operations.OperationSummarized;
import com.fresh.coding.sooatelapi.dtos.operations.OperationWithStock;
import com.fresh.coding.sooatelapi.dtos.pagination.PageInfo;
import com.fresh.coding.sooatelapi.dtos.pagination.Paginate;
import com.fresh.coding.sooatelapi.dtos.searchs.OperationSearch;
import com.fresh.coding.sooatelapi.entities.Operation;
import com.fresh.coding.sooatelapi.repositories.RepositoryFactory;
import com.fresh.coding.sooatelapi.services.operations.OperationService;
import com.fresh.coding.sooatelapi.services.specifications.OperationSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class OperationServiceImpl implements OperationService {

    private final RepositoryFactory repositoryFactory;

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


    private OperationSummarized mapToSummarized(Operation operation) {
        return OperationSummarized.builder()
                .id(operation.getId())
                .type(operation.getType())
                .stockId(operation.getStock().getId())
                .date(operation.getDate())
                .description(operation.getDescription())
                .build();
    }
}

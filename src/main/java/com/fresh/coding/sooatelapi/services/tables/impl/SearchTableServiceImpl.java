package com.fresh.coding.sooatelapi.services.tables.impl;

import com.fresh.coding.sooatelapi.dtos.pagination.PageInfo;
import com.fresh.coding.sooatelapi.dtos.pagination.Paginate;
import com.fresh.coding.sooatelapi.dtos.searchs.TableSearch;
import com.fresh.coding.sooatelapi.dtos.tables.TableSummarized;
import com.fresh.coding.sooatelapi.entities.TableEntity;
import com.fresh.coding.sooatelapi.services.tables.SearchTableService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchTableServiceImpl implements SearchTableService {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public Paginate<List<TableSummarized>> findAllTables(TableSearch tableSearch, int page, int size) {
        var builder = em.getCriteriaBuilder();
        var query = builder.createQuery(TableEntity.class);
        var root = query.from(TableEntity.class);


        var predicates = buildPredicates(tableSearch, builder, root);
        query.where(predicates.toArray(new Predicate[0]));

        query.select(root).distinct(true);

        var typedQuery = em.createQuery(query);
        typedQuery.setFirstResult(page * size);
        typedQuery.setMaxResults(size);

        long totalItems = getTotalCount(tableSearch);
        int totalPages = (int) Math.ceil((double) totalItems / size);

        var tables = typedQuery.getResultList();

        var hasPrevious = page > 0;
        var hasNext = page < totalPages - 1;

        return new Paginate<>(toSummarized(tables), new PageInfo(
                hasNext,
                hasPrevious,
                totalPages,
                page,
                (int) totalItems
        ));
    }


    private List<TableSummarized> toSummarized(List<TableEntity> tables) {
        return tables.stream().map(category -> new TableSummarized(
                category.getId(),
                category.getNumber(),
                category.getCapacity(),
                category.getCreatedAt(),
                category.getUpdatedAt()
        )).collect(Collectors.toList());
    }

    public long getTotalCount(TableSearch tableSearch) {
        var builder = em.getCriteriaBuilder();
        var query = builder.createQuery(Long.class);
        var root = query.from(TableEntity.class);

        var predicates = buildPredicates(tableSearch, builder, root);

        query.select(builder.count(root)).where(predicates.toArray(new Predicate[0]));

        return em.createQuery(query).getSingleResult();
    }


    private List<Predicate> buildPredicates(TableSearch search, CriteriaBuilder builder, Root<TableEntity> root) {
        var predicates = new ArrayList<Predicate>();

        if (search.getCapacityMin() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("capacity"), search.getCapacityMin()));
        }

        if (search.getCapacityMax() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("capacity"), search.getCapacityMax()));
        }

        if (search.getTableNumberMin() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("number"), search.getTableNumberMin()));
        }

        if (search.getTableNumberMax() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("number"), search.getTableNumberMax()));
        }

        return predicates;
    }

}

package com.fresh.coding.sooatelapi.services.purchases.impl;

import com.fresh.coding.sooatelapi.dtos.pagination.PageInfo;
import com.fresh.coding.sooatelapi.dtos.pagination.Paginate;
import com.fresh.coding.sooatelapi.dtos.purchases.PurchaseDTO;
import com.fresh.coding.sooatelapi.dtos.searchs.PurchaseSearch;
import com.fresh.coding.sooatelapi.entities.Purchase;
import com.fresh.coding.sooatelapi.services.purchases.PurchaseService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {
    private final EntityManager entityManager;


    @Override
    public Paginate<List<PurchaseDTO>> findAllPurchases(PurchaseSearch purchaseSearch, int page, int size) {

        var pageable = PageRequest.of(page, size);

        var cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Purchase> query = cb.createQuery(Purchase.class);
        Root<Purchase> root = query.from(Purchase.class);

        List<Predicate> predicates = new ArrayList<>();

        if (purchaseSearch.getIngredientId() != null) {
            predicates.add(cb.equal(root.get("ingredient").get("id"), purchaseSearch.getIngredientId()));
        }

        if (purchaseSearch.getStartDate() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), purchaseSearch.getStartDate()));
        }

        if (purchaseSearch.getEndDate() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("createdAt"), purchaseSearch.getEndDate()));
        }

        query.where(predicates.toArray(new Predicate[0]));

        List<Purchase> purchaseList = entityManager.createQuery(query)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        List<PurchaseDTO> purchaseDTOList = purchaseList.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

        long totalItems = getTotalCount(cb, predicates);
        int totalPages = (int) Math.ceil((double) totalItems / size);

        var pageInfo = new PageInfo(
                page < totalPages - 1,
                page > 0,
                totalPages,
                page,
                (int) totalItems
        );
        return new Paginate<>(purchaseDTOList,pageInfo);
    }

    private long getTotalCount(CriteriaBuilder cb, List<Predicate> predicates) {
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Purchase> root = countQuery.from(Purchase.class);
        countQuery.select(cb.count(root)).where(predicates.toArray(new Predicate[0]));
        return entityManager.createQuery(countQuery).getSingleResult();
    }

    private PurchaseDTO mapToDTO(Purchase purchase) {
        return new PurchaseDTO(
                purchase.getId(),
                purchase.getIngredient().getName(),
                purchase.getIngredient().getId().toString(),
                purchase.getQuantity(),
                purchase.getCost(),
                purchase.getDescription(),
                purchase.getCreatedAt(),
                purchase.getUpdatedAt()
        );
    }

}

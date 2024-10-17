package com.fresh.coding.sooatelapi.services.specifications;

import com.fresh.coding.sooatelapi.dtos.searchs.OperationSearch;
import com.fresh.coding.sooatelapi.entities.Operation;
import com.fresh.coding.sooatelapi.enums.OperationType;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class OperationSpec {

    public static Specification<Operation> hasStockId(Long stockId) {
        return (root, query, criteriaBuilder) -> {
            if (stockId != null) {
                return criteriaBuilder.equal(root.get("stock").get("id"), stockId);
            }
            return criteriaBuilder.conjunction();
        };
    }

    public static Specification<Operation> hasType(OperationType type) {
        return (root, query, criteriaBuilder) -> {
            if (type != null) {
                return criteriaBuilder.equal(root.get("type"), type);
            }
            return criteriaBuilder.conjunction();
        };
    }

    public static Specification<Operation> hasStartDate(LocalDateTime startDate) {
        return (root, query, criteriaBuilder) -> {
            if (startDate != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("date"), startDate);
            }
            return criteriaBuilder.conjunction();
        };
    }

    public static Specification<Operation> hasEndDate(LocalDateTime endDate) {
        return (root, query, criteriaBuilder) -> {
            if (endDate != null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("date"), endDate);
            }
            return criteriaBuilder.conjunction();
        };
    }

    public static Specification<Operation> filter(OperationSearch search) {
        return Specification
                .where(hasStockId(search.getStockId()))
                .and(hasType(search.getType()))
                .and(hasStartDate(search.getStartDate()))
                .and(hasEndDate(search.getEndDate()));
    }
}

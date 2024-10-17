package com.fresh.coding.sooatelapi.dtos.specifications;


import com.fresh.coding.sooatelapi.entities.Operation;
import org.springframework.data.jpa.domain.Specification;

public class OperationSpec {
    public static Specification<Operation> hasStockId(Long stockId) {
        return (root, query, criteriaBuilder) -> {
            if (stockId != null) {
                return criteriaBuilder.equal(
                        root.get("stock"),
                        "%" + stockId + "%"
                );
            }
            return null;
        };
    }

}

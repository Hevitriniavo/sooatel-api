package com.fresh.coding.sooatelapi.repositories;

import com.fresh.coding.sooatelapi.entities.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long>, JpaSpecificationExecutor<Operation> {

    @Query("""
     SELECT o FROM Operation o INNER JOIN o.stock WHERE o.stock.id = :stockId
""")
    List<Operation> findOperationsByStockId(Long stockId);
}

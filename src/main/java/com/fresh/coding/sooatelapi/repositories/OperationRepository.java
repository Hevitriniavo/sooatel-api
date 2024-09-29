package com.fresh.coding.sooatelapi.repositories;

import com.fresh.coding.sooatelapi.entities.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {
}

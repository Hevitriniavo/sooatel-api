package com.fresh.coding.sooatelapi.repositories;

import com.fresh.coding.sooatelapi.entities.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {
}

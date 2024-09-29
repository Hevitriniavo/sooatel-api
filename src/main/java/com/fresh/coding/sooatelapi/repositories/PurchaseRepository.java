package com.fresh.coding.sooatelapi.repositories;

import com.fresh.coding.sooatelapi.entities.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}

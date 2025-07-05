package com.fresh.coding.sooatelapi.repositories;

import com.fresh.coding.sooatelapi.entities.InvoiceLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceLineRepository extends JpaRepository<InvoiceLine, Long> {
}

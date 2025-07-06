package com.fresh.coding.sooatelapi.repositories;

import com.fresh.coding.sooatelapi.entities.Invoice;
import com.fresh.coding.sooatelapi.entities.SessionOccupation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    boolean existsBySessionOccupation(SessionOccupation sessionOccupation);
}

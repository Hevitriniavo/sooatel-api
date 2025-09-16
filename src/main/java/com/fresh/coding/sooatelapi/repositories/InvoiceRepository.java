package com.fresh.coding.sooatelapi.repositories;

import com.fresh.coding.sooatelapi.entities.Invoice;
import com.fresh.coding.sooatelapi.entities.Order;
import com.fresh.coding.sooatelapi.entities.SessionOccupation;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    boolean existsBySessionOccupation(SessionOccupation sessionOccupation);
    @Query("""
        SELECT DISTINCT i FROM Invoice i
        LEFT JOIN FETCH i.order o
        LEFT JOIN FETCH o.table t
        LEFT JOIN FETCH o.room r
        LEFT JOIN FETCH i.lines l
        LEFT JOIN FETCH l.menu m
        ORDER BY i.issuedAt DESC
        """)
    List<Invoice> findAllWithDetailsOrderByIssuedAtDesc();

    @Query("""
        SELECT i FROM Invoice i
        LEFT JOIN FETCH i.order o
        LEFT JOIN FETCH o.table t
        LEFT JOIN FETCH o.room r
        LEFT JOIN FETCH i.lines l
        LEFT JOIN FETCH l.menu m
        WHERE i.id = :invoiceId
        """)
    Optional<Invoice> findInvoiceWithDetailsById(@Param("invoiceId") Long invoiceId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Invoice i WHERE i.order IS NULL")
    void deleteInvoicesWithNullOrder();

    @Modifying
    @Transactional
    @Query("DELETE FROM Invoice i WHERE i.lines IS EMPTY")
    void deleteOrphanInvoices();

    List<Invoice> findAllByOrder(Order order);
}

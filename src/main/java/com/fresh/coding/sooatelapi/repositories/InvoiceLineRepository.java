package com.fresh.coding.sooatelapi.repositories;

import com.fresh.coding.sooatelapi.entities.InvoiceLine;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceLineRepository extends JpaRepository<InvoiceLine, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM InvoiceLine il WHERE il.menu.id = :menuId")
    void deleteByMenuId(@Param("menuId") Long menuId);
}

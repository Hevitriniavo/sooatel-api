package com.fresh.coding.sooatelapi.repositories;

import com.fresh.coding.sooatelapi.entities.Menu;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM invoice_lines WHERE menu_id = :menuId", nativeQuery = true)
    void deleteInvoiceLinesByMenuId(@Param("menuId") Long menuId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM order_line WHERE menu_id = :menuId", nativeQuery = true)
    void deleteOrderLinesByMenuId(@Param("menuId") Long menuId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM menu_ingredient WHERE menu_id = :menuId", nativeQuery = true)
    void deleteMenuIngredientsByMenuId(@Param("menuId") Long menuId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM menu WHERE id = :menuId", nativeQuery = true)
    void deleteMenuByIdNative(@Param("menuId") Long menuId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM invoice WHERE id IN (" +
            "SELECT i.id FROM invoice i " +
            "LEFT JOIN invoice_lines il ON i.id = il.invoice_id " +
            "WHERE il.id IS NULL)", nativeQuery = true)
    void deleteOrphanInvoices();

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM orders WHERE id IN (" +
            "SELECT o.id FROM orders o " +
            "LEFT JOIN order_line ol ON o.id = ol.order_id " +
            "WHERE ol.id IS NULL)", nativeQuery = true)
    void deleteOrphanOrders();

}

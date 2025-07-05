package com.fresh.coding.sooatelapi.repositories;

import com.fresh.coding.sooatelapi.entities.TableEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TableEntityRepository extends JpaRepository<TableEntity, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM TableEntity r WHERE r.id = :restTableId")
    int deleteTableById(@Param("restTableId") Long id);

    @Query("SELECT t FROM TableEntity t WHERE t.number = :number")
    Optional<TableEntity> findByTableNumber(Long number);

    @Query("SELECT DISTINCT t FROM TableEntity t JOIN t.orders o JOIN o.orderLines ol")
    List<TableEntity> findTableWithMenuOrders();

}

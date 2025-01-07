package com.fresh.coding.sooatelapi.repositories;

import com.fresh.coding.sooatelapi.entities.RestTable;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TableRepository extends JpaRepository<RestTable, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM RestTable r WHERE r.id = :restTableId")
    int deleteTableById(@Param("restTableId") Long id);

    Optional<RestTable> findByNumber(Integer aLong);
}

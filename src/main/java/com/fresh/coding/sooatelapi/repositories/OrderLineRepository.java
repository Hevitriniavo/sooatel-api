package com.fresh.coding.sooatelapi.repositories;

import com.fresh.coding.sooatelapi.entities.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM OrderLine ol WHERE ol.menu.id = :menuId")
    void deleteByMenuId(@Param("menuId") Long menuId);
}

package com.fresh.coding.sooatelapi.repositories;

import com.fresh.coding.sooatelapi.entities.Floor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FloorRepository extends JpaRepository<Floor, Long> {
}

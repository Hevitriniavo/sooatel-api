package com.fresh.coding.sooatelapi.repositories;

import com.fresh.coding.sooatelapi.entities.Role;
import com.fresh.coding.sooatelapi.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}

package com.fresh.coding.sooatelapi.repositories;

import com.fresh.coding.sooatelapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailOrUsername(String email, String username);

    Optional<User> findByEmail(String email);
}

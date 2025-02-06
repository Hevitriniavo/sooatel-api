package com.fresh.coding.sooatelapi.repositories;

import com.fresh.coding.sooatelapi.entities.OtpCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface OtpCodeRepository extends JpaRepository<OtpCode, Long> {

    Optional<OtpCode> findByUserEmail(String email);

    @Modifying
    @Transactional
    @Query("DELETE FROM OtpCode o WHERE o.user.id = :userId")
    void deleteAllByUserId(Long userId);

}

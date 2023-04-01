package com.shshon.mypet.auth.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface EmailVerificationRepository extends JpaRepository<EmailVerification, Long> {
    Optional<EmailVerification> findByEmail(String email);

    Optional<EmailVerification> findByCode(UUID code);

    @Modifying
    @Query("delete from EmailVerification ev " +
            "where ev.email = :email and ev.verifiedAt is null ")
    void deleteAllByEmailAndVerifiedAtIsNull(String email);
}

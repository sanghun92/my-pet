package com.shshon.mypet.auth.dto;

import com.shshon.mypet.auth.domain.EmailVerification;

import java.io.Serializable;
import java.time.LocalDateTime;

public record EmailVerificationDto(String code,
                                   String email,
                                   LocalDateTime verifiedAt,
                                   Boolean isVerified
) implements Serializable {
    public static EmailVerificationDto from(EmailVerification emailVerification) {
        return new EmailVerificationDto(
                emailVerification.getCode(),
                emailVerification.getEmail(),
                emailVerification.getVerifiedAt(),
                emailVerification.isVerified()
        );
    }
}

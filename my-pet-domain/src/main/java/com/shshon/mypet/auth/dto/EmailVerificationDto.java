package com.shshon.mypet.auth.dto;

import com.shshon.mypet.auth.domain.EmailVerification;

import java.io.Serializable;
import java.time.LocalDateTime;

public record EmailVerificationDto(LocalDateTime verifiedAt, Boolean isVerified) implements Serializable {
    public static EmailVerificationDto from(EmailVerification emailVerification) {
        return new EmailVerificationDto(emailVerification.getVerifiedAt(), emailVerification.isVerified());
    }
}

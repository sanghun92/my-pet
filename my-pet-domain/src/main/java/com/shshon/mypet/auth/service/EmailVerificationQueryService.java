package com.shshon.mypet.auth.service;

import com.shshon.mypet.auth.domain.EmailVerification;
import com.shshon.mypet.auth.domain.EmailVerificationRepository;
import com.shshon.mypet.auth.exception.EmailVerificationNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailVerificationQueryService {

    private final EmailVerificationRepository emailVerificationRepository;

    public EmailVerification findByEmail(String email) {
        EmailVerification emailVerification = emailVerificationRepository.findByEmail(email)
                .orElseGet(() -> EmailVerification.nonCode(email));
        emailVerification.changeCertificationCode();
        return emailVerification;
    }

    public EmailVerification findByCode(String code) {
        return emailVerificationRepository.findByCode(UUID.fromString(code))
                .orElseThrow(EmailVerificationNotFoundException::new);
    }
}

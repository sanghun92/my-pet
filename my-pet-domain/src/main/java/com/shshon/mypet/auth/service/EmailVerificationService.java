package com.shshon.mypet.auth.service;

import com.shshon.mypet.auth.domain.EmailVerification;
import com.shshon.mypet.auth.domain.EmailVerificationRepository;
import com.shshon.mypet.auth.exception.EmailVerificationNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class EmailVerificationService {

    private final EmailVerificationRepository emailVerificationRepository;

    public EmailVerification createEmailVerification(String email) {
        Optional<EmailVerification> optionalEmailVerification = emailVerificationRepository.findByEmail(email);
        EmailVerification emailVerification;
        if (optionalEmailVerification.isPresent()) {
            emailVerification = optionalEmailVerification.get();
            emailVerification.changeCertificationCode();
        }

        emailVerification = optionalEmailVerification.orElseGet(
                () -> emailVerificationRepository.save(EmailVerification.randomCode(email))
        );
        return emailVerification;
    }

    public EmailVerification findByCode(String code) {
        return emailVerificationRepository.findByCode(UUID.fromString(code))
                .orElseThrow(EmailVerificationNotFoundException::new);
    }

    public EmailVerification findByEmailOrDefault(String email, Supplier<EmailVerification> defaultAction) {
        return emailVerificationRepository.findByEmail(email)
                .orElseGet(defaultAction);
    }

    public void deleteAllByEmailAndVerifiedAtIsNull(String email) {
        emailVerificationRepository.deleteAllByEmailAndVerifiedAtIsNull(email);
    }
}

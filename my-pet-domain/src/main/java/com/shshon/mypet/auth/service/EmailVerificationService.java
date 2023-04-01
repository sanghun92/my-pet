package com.shshon.mypet.auth.service;

import com.shshon.mypet.auth.domain.EmailVerification;
import com.shshon.mypet.auth.domain.EmailVerificationRepository;
import com.shshon.mypet.auth.dto.EmailVerificationDto;
import com.shshon.mypet.auth.exception.EmailVerificationNotFoundException;
import com.shshon.mypet.email.dto.MailMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailVerificationService {

    private final EmailVerificationRepository emailVerificationRepository;

    public EmailVerification generateEmailVerificationCode(String email) {
        return emailVerificationRepository.save(EmailVerification.randomCode(email));
    }

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

    public EmailVerification findByEmail(String email) {
        return emailVerificationRepository.findByEmail(email)
                .orElseThrow(EmailVerificationNotFoundException::new);
    }

    public void deleteAllByEmailAndVerifiedAtIsNull(String email) {
        emailVerificationRepository.deleteAllByEmailAndVerifiedAtIsNull(email);
    }

    public MailMessageDto generateVerifyCodeMailMessage(EmailVerificationDto emailVerification, String url) {
        Map<String, String> attribute = new HashMap<>();
        attribute.put("link", url);

        return MailMessageDto.builder()
                .to(new String[]{emailVerification.email()})
                .subject("[My Pet] 가입 인증 메일입니다.")
                .template("memberCertificationMail")
                .attribute(attribute)
                .build();
    }
}

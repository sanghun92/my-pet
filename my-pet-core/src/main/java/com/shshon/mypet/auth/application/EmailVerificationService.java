package com.shshon.mypet.auth.application;

import com.shshon.mypet.auth.domain.EmailVerification;
import com.shshon.mypet.auth.domain.EmailVerificationRepository;
import com.shshon.mypet.auth.exception.EmailVerificationNotFoundException;
import com.shshon.mypet.email.application.EmailService;
import com.shshon.mypet.email.dto.MailMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class EmailVerificationService {

    private final EmailService emailService;
    private final EmailVerificationRepository emailVerificationRepository;

    @Transactional
    public void sendEmailVerification(String email) {
        EmailVerification emailVerification = emailVerificationRepository.findByEmail(email)
                .orElseGet(() -> EmailVerification.nonCode(email));
        emailVerification.changeCertificationCode();

        String certificationLink = String.format("http://localhost/join/email-verification?code=%s", emailVerification.getCode());

        Map<String, String> attribute = new HashMap<>();
        attribute.put("name", email);
        attribute.put("link", certificationLink);

        MailMessageDto mailMessageDto = MailMessageDto.builder()
                .to(new String[]{email})
                .subject("[My Pet] 가입 인증 메일입니다.")
                .template("memberCertificationMail")
                .attribute(attribute)
                .build();
        emailService.send(mailMessageDto);
    }

    @Transactional
    public void verifyEmail(String code) {
        EmailVerification emailVerification = emailVerificationRepository.findByCode(UUID.fromString(code))
                .orElseThrow(EmailVerificationNotFoundException::new);
        emailVerification.verify();
    }
}

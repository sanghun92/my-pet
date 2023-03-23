package com.shshon.mypet.auth.application;

import com.shshon.mypet.auth.domain.EmailVerification;
import com.shshon.mypet.auth.domain.EmailVerificationRepository;
import com.shshon.mypet.email.application.EmailService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class EmailVerificationServiceTest {

    @Mock
    private EmailService emailService;

    @Mock
    private EmailVerificationRepository emailVerificationRepository;

    @InjectMocks
    private EmailVerificationService emailVerificationService;

    @Test
    @DisplayName("회원 가입시 이메일 인증 메일을 보낸다.")
    void sendCertificatedEmailWhenMemberIsJoinedTest() {
        // given
        String email = "test@test.com";
        willDoNothing().given(emailService).send(any());
        given(emailVerificationRepository.findByEmail(email)).willReturn(Optional.of(EmailVerification.nonCode(email)));

        // when
        emailVerificationService.sendEmailVerification(email);

        // then
        then(emailService).should(times(1)).send(any());
    }

    @Test
    @DisplayName("이메일 인증에 성공한다.")
    void verifyEmailTest() {
        // given
        UUID code = UUID.randomUUID();
        String email = "test@test.com";
        EmailVerification emailVerification = EmailVerification.nonCode(email);
        given(emailVerificationRepository.findByCode(code)).willReturn(Optional.of(emailVerification));

        // when
        emailVerificationService.verifyEmail(code.toString());

        // then
        then(emailVerificationRepository).should(times(1)).findByCode(code);
        assertThat(emailVerification.isVerified()).isTrue();
    }
}

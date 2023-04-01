package com.shshon.mypet.auth.service;

import com.shshon.mypet.auth.domain.EmailVerification;
import com.shshon.mypet.auth.domain.EmailVerificationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class EmailVerificationServiceTest {

    @Mock
    private EmailVerificationRepository emailVerificationRepository;

    @InjectMocks
    private EmailVerificationService emailVerificationService;

    @Test
    @DisplayName("주어진 이메일 주소에 해당되는 이메일 인증 코드를 반환한다")
    void findByEmailTest() {
        // given
        String email = "test@test.com";
        EmailVerification expectedEmailVerification = EmailVerification.randomCode(email);
        given(emailVerificationRepository.findByEmail(email)).willReturn(Optional.of(expectedEmailVerification));

        // when
        EmailVerification emailVerification = emailVerificationService.generateEmailVerificationCode(email);

        // then
        then(emailVerificationRepository).should(times(1)).findByEmail(email);
        assertThat(emailVerification).isEqualTo(expectedEmailVerification);
    }

    @Test
    @DisplayName("주어진 코드에 해당되는 이메일 인증 코드를 반환한다")
    void findByCodeTest() {
        // given
        UUID code = UUID.randomUUID();
        String email = "test@test.com";
        EmailVerification expectedEmailVerification = EmailVerification.randomCode(email);
        given(emailVerificationRepository.findByCode(code)).willReturn(Optional.of(expectedEmailVerification));

        // when
        EmailVerification emailVerification = emailVerificationService.findByCode(code.toString());

        // then
        then(emailVerificationRepository).should(times(1)).findByCode(code);
        assertThat(emailVerification).isEqualTo(expectedEmailVerification);
    }
}

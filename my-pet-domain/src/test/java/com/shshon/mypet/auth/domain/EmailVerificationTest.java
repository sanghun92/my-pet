package com.shshon.mypet.auth.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class EmailVerificationTest {

    @Test
    @DisplayName("이메일 인증에 성공한다")
    void onCertificateMemberTest() {
        // given
        EmailVerification emailVerification = EmailVerification.nonCode("test@test.com");
        boolean beforeOnCertificatedMember = emailVerification.isVerified();

        // when
        emailVerification.verify();

        // then
        assertAll(
                () -> assertThat(beforeOnCertificatedMember).isFalse(),
                () -> assertThat(emailVerification.isVerified()).isTrue()
        );
    }
}

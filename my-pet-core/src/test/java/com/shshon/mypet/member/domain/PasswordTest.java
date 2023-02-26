package com.shshon.mypet.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PasswordTest {

    @Test
    @DisplayName("패스워드 생성시 입력값은 암호화한다.")
    void createPasswordTest() {
        String passwordInput = "123456";

        Password password = Password.of(passwordInput);

        assertThat(password.getValue()).isNotEqualTo(passwordInput);
    }

    @Test
    @DisplayName("암호화 된 패스워드의 일치 여부를 반환한다.")
    void matchPasswordTest() {
        String passwordInput = "123456";

        Password password = Password.of(passwordInput);

        assertThat(password.matches(passwordInput)).isTrue();
    }
}

package com.shshon.mypet.member.domain;

import com.shshon.mypet.member.exception.AuthorizationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class MemberTest {

    private String email;
    private String password;
    private Member member;

    @BeforeEach
    void setUp() {
        this.email = "test@test.com";
        this.password = "qwer1111@";
        member = Member.builder()
                .email(email)
                .password(password)
                .nickname("myNickName")
                .certification(MemberCertification.randomCode())
                .birthDay(LocalDate.now())
                .phoneNumber("01011112222")
                .build();
    }

    @Test
    @DisplayName("회원 인증에 성공한다")
    void onCertificateMemberTest() {
        // given
        boolean beforeOnCertificatedMember = member.isCertificated();

        // when
        member.onCertificate();

        // then
        assertAll(
                () -> assertThat(beforeOnCertificatedMember).isFalse(),
                () -> assertThat(member.isCertificated()).isTrue()
        );
    }

    @Test
    @DisplayName("회원 패스워드 인증에 성공한다")
    void authenticateMemberPasswordTest() {
        // when & then
        member.authenticate(password);
    }

    @Test
    @DisplayName("잘못 된 패스워드가 주어지면 패스워드 인증시 예외처리한다")
    void givenInValidPasswordAuthenticateMemberPasswordTest() {
        // given
        String inValidPassword = "InValidPassword!";

        // when & then
        assertThatThrownBy(() -> member.authenticate(inValidPassword))
                .isInstanceOf(AuthorizationException.class);
    }


    @Test
    @DisplayName("회원 패스워드 변경에 성공한다")
    void changeMemberPasswordTest() {
        // given
        String newPassword = "newPass1234!";

        // when
        member.changePassword(newPassword);

        // then
        member.authenticate(newPassword);
    }
}

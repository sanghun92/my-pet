package com.shshon.mypet.auth.application;

import com.shshon.mypet.auth.domain.EmailVerification;
import com.shshon.mypet.auth.domain.HttpRequestClient;
import com.shshon.mypet.auth.domain.RefreshToken;
import com.shshon.mypet.auth.dto.TokenDto;
import com.shshon.mypet.auth.service.EmailVerificationQueryService;
import com.shshon.mypet.auth.service.TokenService;
import com.shshon.mypet.email.application.EmailService;
import com.shshon.mypet.email.dto.MailMessageDto;
import com.shshon.mypet.member.domain.Member;
import com.shshon.mypet.member.dto.MemberDto;
import com.shshon.mypet.member.service.MemberQueryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class AuthFacadeTest {

    @Mock
    private MemberQueryService memberQueryService;

    @Mock
    private TokenService tokenService;

    @Mock
    private EmailVerificationQueryService emailVerificationQueryService;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private AuthFacade authFacade;

    @Test
    @DisplayName("로그인 요청시 인증 후 토큰을 반환한다")
    void loginTest() {
        // given
        String email = "test@test.com";
        String password = "pass1234!";
        HttpRequestClient client = new HttpRequestClient("0.0.0.1");
        Member member = MemberDto.builder()
                .id(1L)
                .email(email)
                .password(password)
                .build()
                .toMember();
        String accessToken = UUID.randomUUID().toString();
        RefreshToken refreshToken = new RefreshToken(member.getId(), member.getEmail(), "0.0.0.1");
        given(memberQueryService.findByEmail(email)).willReturn(member);
        given(tokenService.createAccessTokenBy(email)).willReturn(accessToken);
        given(tokenService.createRefreshToken(member, client)).willReturn(refreshToken);

        // when
        TokenDto tokenDto = authFacade.login(email, password, client);

        // then
        then(memberQueryService).should(times(1)).findByEmail(email);
        then(tokenService).should(times(1)).createAccessTokenBy(email);
        then(tokenService).should(times(1)).createRefreshToken(member, client);
        assertAll(
                () -> assertThat(tokenDto.accessToken()).isEqualTo(accessToken),
                () -> assertThat(tokenDto.refreshToken().token()).isEqualTo(refreshToken.token()),
                () -> assertThat(tokenDto.refreshToken().memberId()).isEqualTo(member.getId()),
                () -> assertThat(tokenDto.refreshToken().memberEmail()).isEqualTo(member.getEmail()),
                () -> assertThat(tokenDto.refreshToken().ip()).isEqualTo(client.ip())
        );
    }

    @Test
    @DisplayName("주어진 Refresh Token으로 Access Token을 재발급한다")
    void reIssueTokenTest() {
        // given
        String email = "test@test.com";
        String accessToken = UUID.randomUUID().toString();
        String refreshToken = UUID.randomUUID().toString();
        RefreshToken foundRefreshToken = new RefreshToken(1L, email, "0.0.0.1");

        given(tokenService.findRefreshTokenBy(refreshToken)).willReturn(foundRefreshToken);
        given(tokenService.createAccessTokenBy(email)).willReturn(accessToken);

        // when
        TokenDto tokenDto = authFacade.reIssueToken(refreshToken);

        // then
        then(tokenService).should(times(1)).findRefreshTokenBy(refreshToken);
        then(tokenService).should(times(1)).createAccessTokenBy(email);
        assertAll(
                () -> assertThat(tokenDto.accessToken()).isEqualTo(accessToken),
                () -> assertThat(tokenDto.refreshToken().token()).isEqualTo(foundRefreshToken.token())
        );
    }

    @Test
    @DisplayName("회원 가입시 이메일 인증 메일을 보낸다.")
    void sendCertificatedEmailWhenMemberIsJoinedTest() {
        // given
        String email = "test@test.com";
        EmailVerification emailVerification = EmailVerification.nonCode(email);
        emailVerification.changeCertificationCode();
        given(emailVerificationQueryService.findByEmail(email)).willReturn(emailVerification);
        willDoNothing().given(emailService).send(any(MailMessageDto.class));

        // when
        authFacade.sendEmailVerification(email);

        // then
        then(emailVerificationQueryService).should(times(1)).findByEmail(email);
        then(emailService).should(times(1)).send(any(MailMessageDto.class));
    }

    @Test
    @DisplayName("이메일 인증에 성공한다")
    void verifyEmailTest() {
        // given
        String code = UUID.randomUUID().toString();
        String email = "test@test.com";
        EmailVerification emailVerification = EmailVerification.nonCode(email);
        given(emailVerificationQueryService.findByCode(code)).willReturn(emailVerification);

        // when
        authFacade.verifyEmail(code);

        // then
        then(emailVerificationQueryService).should(times(1)).findByCode(code);
        assertThat(emailVerification.isVerified()).isTrue();
    }
}

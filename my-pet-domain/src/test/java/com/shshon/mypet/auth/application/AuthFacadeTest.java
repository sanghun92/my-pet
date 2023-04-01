package com.shshon.mypet.auth.application;

import com.shshon.mypet.auth.domain.EmailVerification;
import com.shshon.mypet.auth.domain.HttpRequestClient;
import com.shshon.mypet.auth.domain.RefreshToken;
import com.shshon.mypet.auth.dto.EmailVerificationDto;
import com.shshon.mypet.auth.dto.TokenDto;
import com.shshon.mypet.auth.service.EmailVerificationService;
import com.shshon.mypet.auth.service.TokenService;
import com.shshon.mypet.email.application.EmailService;
import com.shshon.mypet.email.dto.MailMessageDto;
import com.shshon.mypet.member.domain.Member;
import com.shshon.mypet.member.dto.MemberDto;
import com.shshon.mypet.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
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
    private MemberService memberService;

    @Mock
    private TokenService tokenService;

    @Mock
    private EmailVerificationService emailVerificationService;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private AuthFacade authFacade;

    private String email;
    private String password;
    private Member member;
    private HttpRequestClient client;

    @BeforeEach
    void setUp() {
        this.email = "test@test.com";
        this.password = "pass1234!";
        this.member = MemberDto.builder()
                .id(1L)
                .email(this.email)
                .password(this.password)
                .build()
                .toMember();
        this.client = new HttpRequestClient("0.0.0.1", "PC");
    }

    @Test
    @DisplayName("로그인 요청시 인증 후 토큰을 반환한다")
    void loginTest() {
        // given
        String accessToken = UUID.randomUUID().toString();
        RefreshToken refreshToken = new RefreshToken(member, client);
        given(memberService.findByEmail(email)).willReturn(member);
        given(tokenService.createTokens(member, client)).willReturn(new TokenDto(accessToken, refreshToken));

        // when
        TokenDto tokenDto = authFacade.login(email, password, client);

        // then
        then(memberService).should(times(1)).findByEmail(email);
        then(tokenService).should(times(1)).createTokens(member, client);
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
        HttpRequestClient client = new HttpRequestClient("0.0.0.1", "PC");
        RefreshToken foundRefreshToken = new RefreshToken(1L, email, client.ip(), client.userAgent());

        given(tokenService.findRefreshTokenBy(refreshToken)).willReturn(foundRefreshToken);
        given(memberService.findById(any(Long.class))).willReturn(member);
        given(tokenService.createAccessTokenBy(member, client)).willReturn(accessToken);

        // when
        TokenDto tokenDto = authFacade.reIssueToken(refreshToken, client);

        // then
        then(tokenService).should(times(1)).findRefreshTokenBy(refreshToken);
        then(tokenService).should(times(1)).createAccessTokenBy(member, client);
        assertAll(
                () -> assertThat(tokenDto.accessToken()).isEqualTo(accessToken),
                () -> assertThat(tokenDto.refreshToken().token()).isEqualTo(foundRefreshToken.token())
        );
    }

    @Test
    @DisplayName("이메일 인증 코드를 메일 전송하는데 성공한다")
    void sendEmailVerificationCodeWhenMemberIsJoinedTest() {
        // given
        String email = "test@test.com";
        EmailVerification emailVerification = EmailVerification.randomCode(email);
        String url = "http://localhost/join/email-verification?code=" + emailVerification.getCode() + "&email=" + emailVerification.getEmail();
        given(emailVerificationService.generateVerifyCodeMailMessage(any(EmailVerificationDto.class), eq(url))).willReturn(MailMessageDto.builder().build());
        willDoNothing().given(emailService).send(any(MailMessageDto.class));

        // when
        authFacade.sendEmailVerificationCode(EmailVerificationDto.from(emailVerification), url);

        // then
        then(emailVerificationService).should(times(1)).generateVerifyCodeMailMessage(any(EmailVerificationDto.class), eq(url));
        then(emailService).should(times(1)).send(any(MailMessageDto.class));
    }

    @Test
    @DisplayName("이메일 인증에 성공한다")
    void verifyEmailTest() {
        // given
        String code = UUID.randomUUID().toString();
        String email = "test@test.com";
        EmailVerification emailVerification = EmailVerification.randomCode(email);
        given(emailVerificationService.findByCode(code)).willReturn(emailVerification);

        // when
        authFacade.verifyEmailVerificationCode(code, email);

        // then
        then(emailVerificationService).should(times(1)).findByCode(code);
        assertThat(emailVerification.isVerified()).isTrue();
    }
}

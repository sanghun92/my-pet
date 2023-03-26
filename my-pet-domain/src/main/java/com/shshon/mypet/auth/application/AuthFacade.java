package com.shshon.mypet.auth.application;

import com.shshon.mypet.Facade;
import com.shshon.mypet.auth.domain.EmailVerification;
import com.shshon.mypet.auth.domain.HttpRequestClient;
import com.shshon.mypet.auth.domain.RefreshToken;
import com.shshon.mypet.auth.dto.TokenDto;
import com.shshon.mypet.auth.service.EmailVerificationService;
import com.shshon.mypet.auth.service.TokenService;
import com.shshon.mypet.email.application.EmailService;
import com.shshon.mypet.member.domain.Member;
import com.shshon.mypet.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Facade
@RequiredArgsConstructor
@Slf4j
public class AuthFacade {

    private final MemberService memberService;
    private final TokenService tokenService;
    private final EmailVerificationService emailVerificationService;
    private final EmailService emailService;

    @Transactional
    public TokenDto login(String email, String password, HttpRequestClient client) {
        log.info("로그인 요청 - email : {}", email);
        Member member = memberService.findByEmail(email);
        member.authenticate(password);

        return tokenService.createTokens(member, client);
    }

    public TokenDto reIssueToken(String refreshToken, HttpRequestClient client) {
        log.info("토큰 재발급 요청 - Refresh Token : {}", refreshToken);
        RefreshToken foundRefreshToken = tokenService.findRefreshTokenBy(refreshToken);
        Member member = memberService.findById(foundRefreshToken.memberId());

        String newAccessToken = tokenService.createAccessTokenBy(member, client);
        return new TokenDto(newAccessToken, foundRefreshToken);
    }

    @Transactional(readOnly = true)
    public void sendEmailVerification(String email) {
        log.info("이메일 인증 발송 요청 - email : {}", email);
        EmailVerification emailVerification = emailVerificationService.findByEmail(email);
        emailService.send(emailVerification.generateVerificationMailMessage());
    }

    @Transactional
    public void verifyEmail(String code) {
        log.info("이메일 인증 요청 - code : {}", code);
        EmailVerification emailVerification = emailVerificationService.findByCode(code);
        emailVerification.verify();
    }
}

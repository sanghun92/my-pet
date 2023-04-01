package com.shshon.mypet.auth.application;

import com.shshon.mypet.Facade;
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
import com.shshon.mypet.member.service.MemberService;
import com.shshon.mypet.util.CacheNames;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
    @Cacheable(cacheNames = CacheNames.EMAIL_VERIFICATION, key = "#email")
    public EmailVerificationDto findEmailVerificationBy(String email) {
        EmailVerification emailVerification = emailVerificationService.findByEmail(email);
        return EmailVerificationDto.from(emailVerification);
    }

    @Transactional
    public EmailVerificationDto generateEmailVerificationCode(String email) {
        log.info("이메일 인증 코드 생성 요청 - email : {}", email);
        EmailVerification emailVerification = emailVerificationService.generateEmailVerificationCode(email);
        return EmailVerificationDto.from(emailVerification);
    }

    @Transactional
    public void sendEmailVerificationCode(EmailVerificationDto emailVerification, String url) {
        log.info("이메일 인증 발송 요청 - url : {}", url);
        MailMessageDto mailMessageDto = emailVerificationService.generateVerifyCodeMailMessage(emailVerification, url);
        emailService.send(mailMessageDto);
    }

    @Transactional
    @CacheEvict(cacheNames = CacheNames.EMAIL_VERIFICATION, key = "#email")
    public void verifyEmailVerificationCode(String code, String email) {
        log.info("이메일 인증 요청 - code : {}", code);
        EmailVerification emailVerification = emailVerificationService.findByCode(code);
        emailVerification.verify();
        emailVerificationService.deleteAllByEmailAndVerifiedAtIsNull(email);
    }
}

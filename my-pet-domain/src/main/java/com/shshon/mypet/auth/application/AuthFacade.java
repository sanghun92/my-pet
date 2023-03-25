package com.shshon.mypet.auth.application;

import com.shshon.mypet.Facade;
import com.shshon.mypet.auth.domain.EmailVerification;
import com.shshon.mypet.auth.domain.HttpRequestClient;
import com.shshon.mypet.auth.domain.RefreshToken;
import com.shshon.mypet.auth.dto.TokenDto;
import com.shshon.mypet.auth.service.EmailVerificationQueryService;
import com.shshon.mypet.auth.service.TokenService;
import com.shshon.mypet.email.application.EmailService;
import com.shshon.mypet.email.dto.MailMessageDto;
import com.shshon.mypet.member.domain.Member;
import com.shshon.mypet.member.service.MemberQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Facade
@RequiredArgsConstructor
public class AuthFacade {

    private final MemberQueryService memberQueryService;
    private final TokenService tokenService;
    private final EmailVerificationQueryService emailVerificationQueryService;
    private final EmailService emailService;

    @Transactional
    public TokenDto login(String email, String password, HttpRequestClient client) {
        Member member = memberQueryService.findByEmail(email);
        member.authenticate(password);

        String accessToken = tokenService.createAccessTokenBy(email);
        RefreshToken refreshToken = tokenService.createRefreshToken(member, client);
        return new TokenDto(accessToken, refreshToken);
    }

    public TokenDto reIssueToken(String refreshToken) {
        RefreshToken foundRefreshToken = tokenService.findRefreshTokenBy(refreshToken);
        String newAccessToken = tokenService.createAccessTokenBy(foundRefreshToken.memberEmail());
        return new TokenDto(newAccessToken, foundRefreshToken);
    }

    @Transactional
    public void sendEmailVerification(String email) {
        EmailVerification emailVerification = emailVerificationQueryService.findByEmail(email);
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
        EmailVerification emailVerification = emailVerificationQueryService.findByCode(code);
        emailVerification.verify();
    }
}

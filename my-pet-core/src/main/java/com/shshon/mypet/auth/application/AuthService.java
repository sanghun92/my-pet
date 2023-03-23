package com.shshon.mypet.auth.application;

import com.shshon.mypet.auth.domain.HttpRequestClient;
import com.shshon.mypet.auth.domain.LoginMember;
import com.shshon.mypet.auth.domain.RefreshToken;
import com.shshon.mypet.auth.domain.RefreshTokenRepository;
import com.shshon.mypet.auth.dto.TokenDto;
import com.shshon.mypet.auth.infra.JwtTokenProvider;
import com.shshon.mypet.member.domain.Member;
import com.shshon.mypet.member.domain.MemberRepository;
import com.shshon.mypet.member.exception.AuthorizationException;
import com.shshon.mypet.properties.JwtTokenProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtTokenProperties tokenProperties;
    private final RefreshTokenRepository refreshTokenRepository;

    public TokenDto login(String email,
                          String password,
                          HttpRequestClient client) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(AuthorizationException::new);
        member.authenticate(password);

        String accessToken = jwtTokenProvider.createToken(email);
        RefreshToken refreshToken = new RefreshToken(member.getId(), member.getEmail(), client.ip());
        long refreshTokenTimeToLiveSeconds = tokenProperties.refreshToken().validityInSeconds() + 5L;
        refreshTokenRepository.save(refreshToken, refreshTokenTimeToLiveSeconds);

        return new TokenDto(accessToken, refreshToken);
    }

    public LoginMember findMemberByToken(String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            throw new AuthorizationException();
        }
        String email = jwtTokenProvider.getPayload(token);
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(AuthorizationException::new);
        return LoginMember.from(member);
    }

    public TokenDto reIssueToken(String refreshToken) {
        RefreshToken findRefreshToken = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(RuntimeException::new);
        String accessToken = jwtTokenProvider.createToken(findRefreshToken.memberEmail());
        return new TokenDto(accessToken, findRefreshToken);
    }
}

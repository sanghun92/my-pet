package com.shshon.mypet.auth.service;

import com.shshon.mypet.auth.domain.HttpRequestClient;
import com.shshon.mypet.auth.domain.LoginMember;
import com.shshon.mypet.auth.domain.RefreshToken;
import com.shshon.mypet.auth.domain.RefreshTokenRepository;
import com.shshon.mypet.auth.dto.TokenDto;
import com.shshon.mypet.auth.infra.JwtTokenProvider;
import com.shshon.mypet.member.domain.Member;
import com.shshon.mypet.member.exception.AuthorizationException;
import com.shshon.mypet.properties.JwtTokenProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtTokenProperties tokenProperties;
    private final RefreshTokenRepository refreshTokenRepository;

    public String createAccessTokenBy(Member member, HttpRequestClient client) {
        return jwtTokenProvider.createToken(member, client);
    }

    public RefreshToken createRefreshToken(Member member, HttpRequestClient client) {
        RefreshToken refreshToken = new RefreshToken(member, client);
        long refreshTokenTimeToLiveSeconds = tokenProperties.refreshToken().validityInSeconds() + 5L;

        refreshTokenRepository.save(refreshToken, refreshTokenTimeToLiveSeconds);
        return refreshToken;
    }

    public RefreshToken findRefreshTokenBy(String refreshToken) {
        return refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(RuntimeException::new);
    }

    public LoginMember findLoginMemberBy(String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            throw new AuthorizationException(token);
        }
        return jwtTokenProvider.claimsToLoginMember(token);
    }

    public TokenDto createTokens(Member member, HttpRequestClient client) {
        String accessToken = createAccessTokenBy(member, client);
        RefreshToken refreshToken = createRefreshToken(member, client);
        return new TokenDto(accessToken, refreshToken);
    }
}

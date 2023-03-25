package com.shshon.mypet.auth.service;

import com.shshon.mypet.auth.domain.HttpRequestClient;
import com.shshon.mypet.auth.domain.RefreshToken;
import com.shshon.mypet.auth.domain.RefreshTokenRepository;
import com.shshon.mypet.auth.infra.JwtTokenProvider;
import com.shshon.mypet.member.domain.Member;
import com.shshon.mypet.properties.JwtTokenProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtTokenProperties tokenProperties;
    private final RefreshTokenRepository refreshTokenRepository;

    public String createAccessTokenBy(String email) {
        return jwtTokenProvider.createToken(email);
    }

    public RefreshToken createRefreshToken(Member member, HttpRequestClient client) {
        RefreshToken refreshToken = new RefreshToken(member.getId(), member.getEmail(), client.ip());
        long refreshTokenTimeToLiveSeconds = tokenProperties.refreshToken().validityInSeconds() + 5L;

        refreshTokenRepository.save(refreshToken, refreshTokenTimeToLiveSeconds);
        return refreshToken;
    }

    public RefreshToken findRefreshTokenBy(String refreshToken) {
        return refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(RuntimeException::new);
    }
}

package com.shshon.mypet.auth.infra;

import com.shshon.mypet.auth.domain.HttpRequestClient;
import com.shshon.mypet.auth.domain.LoginMember;
import com.shshon.mypet.member.domain.Member;
import com.shshon.mypet.properties.JwtTokenProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProviderImpl implements JwtTokenProvider {

    private final Key accessTokenSecretKey;
    private final Long validityInSeconds;

    public JwtTokenProviderImpl(JwtTokenProperties jwtTokenProperties) {
        JwtTokenProperties.AccessToken accessToken = jwtTokenProperties.accessToken();
        String secretKey = accessToken.secretKey();

        this.accessTokenSecretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        this.validityInSeconds = accessToken.validityInSeconds();
    }

    public String createToken(Member member, HttpRequestClient client) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInSeconds * 1000);

        Claims claims = Jwts.claims()
                .setIssuer("my-pet")
                .setId(String.valueOf(member.getId()))
                .setAudience(client.userAgent())
                .setSubject(member.getEmail())
                .setIssuedAt(now)
                .setExpiration(validity);
        claims.put("ip", client.ip());

        return Jwts.builder()
                .setClaims(claims)
                .signWith(accessTokenSecretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            parseClaims(token).getExpiration().before(new Date());
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public LoginMember claimsToLoginMember(String token) {
        Claims claims = parseClaims(token);
        return new LoginMember(
                Long.parseLong(claims.getId()),
                claims.getSubject(),
                claims.get("ip", String.class),
                claims.getAudience()
        );
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(accessTokenSecretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}

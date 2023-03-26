package com.shshon.mypet.auth.infra;

import com.shshon.mypet.auth.domain.HttpRequestClient;
import com.shshon.mypet.auth.domain.LoginMember;
import com.shshon.mypet.member.domain.Member;
import com.shshon.mypet.member.dto.MemberDto;
import com.shshon.mypet.properties.JwtTokenProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(JwtTokenProperties.class)
@ContextConfiguration(
        initializers = ConfigDataApplicationContextInitializer.class,
        classes = {JwtTokenProviderImpl.class}
)
@TestPropertySource(properties = {
        "security.jwt.accessToken.secret-key=askdfjlkasdfjlksadfjklsadjfalksdfjlaksdjflaksdjfksld",
        "security.jwt.accessToken.validityInSeconds=20",
        "security.jwt.refreshToken.validityInSeconds=20"
})
class JwtTokenProviderImplTest {


    @Autowired
    private JwtTokenProperties tokenProperties;

    @Autowired
    private JwtTokenProviderImpl jwtTokenProviderImpl;

    private Member member;
    private HttpRequestClient client;

    @BeforeEach
    void setUp() {
        this.member = MemberDto.builder().email("test@test.com").password("test").build().toMember();
        this.client = new HttpRequestClient("0.0.0.1", "PC");
    }

    @Test
    @DisplayName("주어진 회원과 요청 정보로 인증 토큰을 생성한다.")
    void createTokenByEmailTest() {
        String token = jwtTokenProviderImpl.createToken(member, client);
        assertThat(jwtTokenProviderImpl.validateToken(token)).isTrue();
    }

    @Test
    @DisplayName("주어진 토큰이 유효한지 확인한다.")
    void validateTokenByEmailTest() {
        String token = jwtTokenProviderImpl.createToken(member, client);
        boolean validateToken = jwtTokenProviderImpl.validateToken(token);
        assertThat(validateToken).isTrue();
    }

    @Test
    @DisplayName("주어진 토큰을 파싱하여 로그인 정보를 반환한다")
    void findLoginMemberByTokenTest() {
        // given
        SecretKey secretKey = Keys.hmacShaKeyFor(tokenProperties.accessToken().secretKey().getBytes(StandardCharsets.UTF_8));
        Date now = new Date();
        Date validity = new Date(now.getTime() + 20 * 1000);
        Claims claims = Jwts.claims()
                .setIssuer("my-pet")
                .setId("1")
                .setAudience(client.userAgent())
                .setSubject(member.getEmail())
                .setIssuedAt(now)
                .setExpiration(validity);
        claims.put("ip", client.ip());

        String token = Jwts.builder()
                .setClaims(claims)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        // when
        LoginMember loginMember = jwtTokenProviderImpl.claimsToLoginMember(token);

        // then
        assertAll(
                () -> assertThat(loginMember.id()).isEqualTo(1L),
                () -> assertThat(loginMember.email()).isEqualTo(member.getEmail()),
                () -> assertThat(loginMember.ip()).isEqualTo(client.ip()),
                () -> assertThat(loginMember.userAgent()).isEqualTo(client.userAgent())
        );
    }
}

package com.shshon.auth.infra;

import com.shshon.mypet.auth.infra.JwtTokenProvider;
import com.shshon.mypet.auth.properties.SecurityProperties;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;
    private static SecurityProperties securityProperties;

    @BeforeAll
    static void setUpAll() {
        SecurityProperties.Token token = new SecurityProperties.Token();
        token.setSecretKey("askdfjlkasdfjlksadfjklsadjfalksdfjlaksdjflaksdjfksld");
        token.setValidityInMilliseconds(2000L);

        SecurityProperties.Jwt jwt = new SecurityProperties.Jwt();
        jwt.setToken(token);

        securityProperties = new SecurityProperties();
        securityProperties.setJwt(jwt);
    }

    @BeforeEach
    void setUp() {
        this.jwtTokenProvider = new JwtTokenProvider(securityProperties);
    }

    @Test
    @DisplayName("주어진 이메일로 인증 토큰을 생성한다.")
    void createTokenByEmailTest() {
        String email = "test@test.com";
        String token = jwtTokenProvider.createToken(email);
        assertThat(jwtTokenProvider.validateToken(token)).isTrue();
    }

    @Test
    @DisplayName("주어진 토큰이 유효한지 확인한다.")
    void validateTokenByEmailTest() throws InterruptedException {
        String email = "test@test.com";
        String token = jwtTokenProvider.createToken(email);
        boolean isValidBeforeSleep = jwtTokenProvider.validateToken(token);

        Thread.sleep(securityProperties.getJwt()
                .getToken()
                .getValidityInMilliseconds()
        );

        assertAll(
                () -> assertThat(isValidBeforeSleep).isTrue(),
                () -> assertThat(jwtTokenProvider.validateToken(token)).isFalse()
        );
    }
}

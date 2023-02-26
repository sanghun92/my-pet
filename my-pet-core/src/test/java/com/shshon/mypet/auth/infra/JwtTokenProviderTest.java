package com.shshon.mypet.auth.infra;

import com.shshon.mypet.properties.TokenProperties;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;
    private static TokenProperties tokenProperties;

    @BeforeAll
    static void setUpAll() {
        tokenProperties = new TokenProperties();
        tokenProperties.setSecretKey("askdfjlkasdfjlksadfjklsadjfalksdfjlaksdjflaksdjfksld");
        tokenProperties.setValidityInMilliseconds(2000L);
    }

    @BeforeEach
    void setUp() {
        this.jwtTokenProvider = new JwtTokenProvider(tokenProperties);
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

        Thread.sleep(tokenProperties.getValidityInMilliseconds());

        assertAll(
                () -> assertThat(isValidBeforeSleep).isTrue(),
                () -> assertThat(jwtTokenProvider.validateToken(token)).isFalse()
        );
    }
}

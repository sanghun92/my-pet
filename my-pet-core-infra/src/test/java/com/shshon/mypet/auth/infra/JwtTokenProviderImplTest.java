package com.shshon.mypet.auth.infra;

import com.shshon.mypet.properties.TokenProperties;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class JwtTokenProviderImplTest {

    private JwtTokenProviderImpl jwtTokenProviderImpl;
    private static TokenProperties tokenProperties;

    @BeforeAll
    static void setUpAll() {
        tokenProperties = new TokenProperties(
                "askdfjlkasdfjlksadfjklsadjfalksdfjlaksdjflaksdjfksld",
                2000L
        );
    }

    @BeforeEach
    void setUp() {
        this.jwtTokenProviderImpl = new JwtTokenProviderImpl(tokenProperties);
    }

    @Test
    @DisplayName("주어진 이메일로 인증 토큰을 생성한다.")
    void createTokenByEmailTest() {
        String email = "test@test.com";
        String token = jwtTokenProviderImpl.createToken(email);
        assertThat(jwtTokenProviderImpl.validateToken(token)).isTrue();
    }

    @Test
    @DisplayName("주어진 토큰이 유효한지 확인한다.")
    void validateTokenByEmailTest() throws InterruptedException {
        String email = "test@test.com";
        String token = jwtTokenProviderImpl.createToken(email);
        boolean isValidBeforeSleep = jwtTokenProviderImpl.validateToken(token);

        Thread.sleep(tokenProperties.validityInMilliseconds());

        assertAll(
                () -> assertThat(isValidBeforeSleep).isTrue(),
                () -> assertThat(jwtTokenProviderImpl.validateToken(token)).isFalse()
        );
    }
}

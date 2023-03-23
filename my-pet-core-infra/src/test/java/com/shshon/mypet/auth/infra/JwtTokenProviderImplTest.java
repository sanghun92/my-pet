package com.shshon.mypet.auth.infra;

import com.shshon.mypet.properties.JwtTokenProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
        "security.jwt.accessToken.validityInSeconds=2",
        "security.jwt.refreshToken.validityInSeconds=2"
})
class JwtTokenProviderImplTest {


    @Autowired
    private JwtTokenProperties tokenProperties;

    @Autowired
    private JwtTokenProviderImpl jwtTokenProviderImpl;

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

        Thread.sleep(tokenProperties.accessToken().validityInSeconds() * 1000);

        assertAll(
                () -> assertThat(isValidBeforeSleep).isTrue(),
                () -> assertThat(jwtTokenProviderImpl.validateToken(token)).isFalse()
        );
    }
}

package com.shshon.mypet.auth.application;

import com.shshon.mypet.auth.domain.HttpRequestClient;
import com.shshon.mypet.auth.domain.RefreshToken;
import com.shshon.mypet.auth.domain.RefreshTokenRepository;
import com.shshon.mypet.auth.dto.TokenDto;
import com.shshon.mypet.auth.infra.JwtTokenProvider;
import com.shshon.mypet.member.domain.Member;
import com.shshon.mypet.member.domain.MemberRepository;
import com.shshon.mypet.member.dto.MemberDto;
import com.shshon.mypet.properties.JwtTokenProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        initializers = ConfigDataApplicationContextInitializer.class,
        classes = {AuthService.class}
)
@EnableConfigurationProperties(JwtTokenProperties.class)
@TestPropertySource(properties = {
        "security.jwt.accessToken.secret-key=test",
        "security.jwt.accessToken.validityInSeconds=2",
        "security.jwt.refreshToken.validityInSeconds=2"
})
class AuthServiceTest {

    @MockBean
    private MemberRepository memberRepository;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private JwtTokenProperties tokenProperties;

    @MockBean
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private AuthService authService;

    @Test
    @DisplayName("로그인 요청시 인증 후 토큰을 반환한다")
    void memberLoginTest() {
        // given
        String email = "test@test.com";
        String password = "pass1234!";
        String token = UUID.randomUUID().toString();
        Member member = MemberDto.builder()
                .email(email)
                .password(password)
                .build()
                .toMember();
        given(memberRepository.findByEmail(email)).willReturn(Optional.of(member));
        given(jwtTokenProvider.createToken(email)).willReturn(token);

        long timeToLiveSeconds = tokenProperties.refreshToken().validityInSeconds() + 5L;
        HttpRequestClient requestClient = new HttpRequestClient("0.0.0.1");

        // when
        TokenDto tokenDto = authService.login(email, password, requestClient);

        // then
        then(memberRepository).should(times(1)).findByEmail(email);
        then(jwtTokenProvider).should(times(1)).createToken(email);
        then(refreshTokenRepository).should(times(1)).save(any(RefreshToken.class), eq(timeToLiveSeconds));
        assertAll(
                () -> assertThat(tokenDto.accessToken()).isEqualTo(token),
                () -> assertThat(tokenDto.refreshToken().token()).isNotEmpty(),
                () -> assertThat(tokenDto.refreshToken().memberId()).isEqualTo(member.getId()),
                () -> assertThat(tokenDto.refreshToken().memberEmail()).isEqualTo(member.getEmail()),
                () -> assertThat(tokenDto.refreshToken().ip()).isEqualTo(requestClient.ip())
        );
    }
}

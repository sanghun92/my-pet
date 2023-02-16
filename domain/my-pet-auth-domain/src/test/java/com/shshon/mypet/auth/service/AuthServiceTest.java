package com.shshon.mypet.auth.service;

import com.shshon.mypet.auth.dto.TokenDto;
import com.shshon.mypet.auth.infra.JwtTokenProvider;
import com.shshon.mypet.auth.service.AuthService;
import com.shshon.mypet.member.domain.Member;
import com.shshon.mypet.member.domain.MemberRepository;
import com.shshon.mypet.member.dto.MemberDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
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

        // when
        TokenDto tokenDto = authService.login(email, password);

        // then
        assertThat(tokenDto.getToken()).isEqualTo(token);
    }
}

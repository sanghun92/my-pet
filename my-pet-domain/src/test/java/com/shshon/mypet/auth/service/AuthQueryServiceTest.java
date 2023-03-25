package com.shshon.mypet.auth.service;

import com.shshon.mypet.auth.domain.LoginMember;
import com.shshon.mypet.auth.infra.JwtTokenProvider;
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
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class AuthQueryServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private AuthQueryService authQueryService;

    @Test
    @DisplayName("주어진 토큰에 해당되는 회원 정보를 반환한다")
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
        given(jwtTokenProvider.validateToken(token)).willReturn(true);
        given(jwtTokenProvider.getPayload(token)).willReturn(email);
        given(memberRepository.findByEmail(email)).willReturn(Optional.of(member));

        // when
        LoginMember loginMember = authQueryService.findMemberByToken(token);

        // then
        then(jwtTokenProvider).should(times(1)).validateToken(token);
        then(jwtTokenProvider).should(times(1)).getPayload(token);
        then(memberRepository).should(times(1)).findByEmail(email);
        assertThat(loginMember.id()).isEqualTo(member.getId());
    }
}

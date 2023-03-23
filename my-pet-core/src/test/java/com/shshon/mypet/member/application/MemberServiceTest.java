package com.shshon.mypet.member.application;

import com.shshon.mypet.auth.domain.LoginMember;
import com.shshon.mypet.member.domain.Member;
import com.shshon.mypet.member.domain.MemberRepository;
import com.shshon.mypet.member.dto.MemberDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private MemberService memberService;

    @Test
    @DisplayName("회원 등록 요청시 등록 후 회원 정보를 반환한다.")
    void createMemberThenReturnNewMemberIdTest() {
        // given
        MemberDto memberDto = MemberDto.builder()
                .email("test@test.com")
                .password("123456")
                .nickname("myNickName")
                .build();
        given(memberRepository.save(any())).willReturn(memberDto.toMember());

        // when
        MemberDto member = memberService.createMember(memberDto);

        // then
        assertAll(
                () -> assertThat(member.email()).isEqualTo(memberDto.email()),
                () -> assertThat(member.nickname()).isEqualTo(memberDto.nickname())
        );
    }

    @Test
    @DisplayName("회원 탈퇴에 성공한다.")
    void deleteMemberTest() {
        // given
        Member member = MemberDto.builder()
                .email("test@test.com")
                .password("123456")
                .nickname("myNickName")
                .build()
                .toMember();
        given(memberRepository.findById(any(Long.class))).willReturn(Optional.of(member));

        // when
        memberService.deleteMember(LoginMember.builder()
                .id(1L)
                .build()
        );
    }
}

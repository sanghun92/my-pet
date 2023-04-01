package com.shshon.mypet.member.service;

import com.shshon.mypet.member.domain.Member;
import com.shshon.mypet.member.domain.MemberRepository;
import com.shshon.mypet.member.dto.MemberDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @Test
    @DisplayName("회원 등록에 성공한다.")
    void createMemberTest() {
        // given
        MemberDto memberDto = MemberDto.builder()
                .email("test@test.com")
                .password("123456")
                .nickname("myNickName")
                .build();
        given(memberRepository.save(any())).willReturn(memberDto.toMember());

        // when
        Member member = memberService.createMember(memberDto);

        // then
        assertAll(
                () -> assertThat(member.getEmail()).isEqualTo(memberDto.email()),
                () -> assertThat(member.getNickname()).isEqualTo(memberDto.nickname())
        );
    }
}

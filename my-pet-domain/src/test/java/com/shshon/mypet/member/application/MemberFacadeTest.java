package com.shshon.mypet.member.application;

import com.shshon.mypet.member.domain.Member;
import com.shshon.mypet.member.dto.MemberDto;
import com.shshon.mypet.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class MemberFacadeTest {

    @Mock
    private MemberService memberService;

    @InjectMocks
    private MemberFacade memberFacade;

    @Test
    @DisplayName("회원 등록 요청시 등록 후 회원 정보를 반환한다")
    void createMemberThenReturnNewMemberIdTest() {
        // given
        MemberDto memberDto = MemberDto.builder()
                .email("test@test.com")
                .password("123456")
                .nickname("myNickName")
                .build();
        given(memberService.createMember(memberDto)).willReturn(memberDto.toMember());

        // when
        MemberDto member = memberFacade.createMember(memberDto);

        // then
        then(memberService).should(times(1)).createMember(memberDto);
        assertAll(
                () -> assertThat(member.email()).isEqualTo(memberDto.email()),
                () -> assertThat(member.nickname()).isEqualTo(memberDto.nickname())
        );
    }

    @Test
    @DisplayName("회원 탈퇴에 성공한다")
    void deleteMemberTest() {
        // given
        long memberId = 1L;
        Member member = MemberDto.builder()
                .email("test@test.com")
                .password("123456")
                .nickname("myNickName")
                .build()
                .toMember();
        given(memberService.findById(memberId)).willReturn(member);

        // when
        memberFacade.deleteMember(memberId);

        // then
        then(memberService).should(times(1)).findById(memberId);
        assertThat(member.isDeleted()).isTrue();
    }
}

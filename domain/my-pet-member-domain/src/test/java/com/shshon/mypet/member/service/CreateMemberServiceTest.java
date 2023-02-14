package com.shshon.mypet.member.service;

import com.shshon.mypet.member.domain.MemberRepository;
import com.shshon.mypet.member.dto.MemberDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CreateMemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private CreateMemberService createMemberService;

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
        MemberDto member = createMemberService.createMember(memberDto);

        // then
        assertAll(
                () -> assertThat(member.getEmail()).isEqualTo(memberDto.getEmail()),
                () -> assertThat(member.getNickname()).isEqualTo(memberDto.getNickname())
        );
    }

}

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

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class MemberUpdateServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberUpdateService memberUpdateService;

    @Test
    @DisplayName("회원 인증에 성공한다.")
    void certificateMemberTest() {
        // given
        UUID certificationCode = UUID.randomUUID();
        Member member = MemberDto.builder()
                .email("test@test.com")
                .password("123456")
                .nickname("myNickName")
                .build()
                .toMember();
        given(memberRepository.findByCertificationCode(certificationCode)).willReturn(Optional.of(member));

        // when
        memberUpdateService.certificateMember(certificationCode.toString());

        // then
        then(memberRepository).should(times(1)).findByCertificationCode(certificationCode);
        assertThat(member.isCertificated()).isTrue();
    }
}

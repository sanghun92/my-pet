package com.shshon.mypet.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MemberTest {

    @Test
    @DisplayName("회원 생성에 성공한다")
    void createMemberTest() {
        Member member = Member.of("test@test.com", "123456", "myNickName");

        assertThat(member).isEqualTo(Member.of("test@test.com", "123456", "myNickName"));
    }
}

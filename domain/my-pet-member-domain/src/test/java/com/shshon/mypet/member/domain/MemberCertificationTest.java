package com.shshon.mypet.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MemberCertificationTest {

    @Test
    @DisplayName("회원 인증 정보를 생성한다.")
    void createMemberCertificationTest() {
        // given && when
        MemberCertification certification = MemberCertification.randomCode();

        // then
        assertThat(certification.isCertificated()).isFalse();
    }

    @Test
    @DisplayName("회원 인증 후 인증 상태값이 변경되었는지 확인한다.")
    void onCertificateMemberTest() {
        // given
        MemberCertification certification = MemberCertification.randomCode();

        // when
        certification = certification.onCertificate();

        // then
        assertThat(certification.isCertificated()).isTrue();
    }
}

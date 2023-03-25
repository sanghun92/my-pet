package com.shshon.mypet.auth.domain;

import com.shshon.mypet.member.domain.Member;
import lombok.Builder;

@Builder
public record LoginMember (Long id) {

    public static LoginMember from(Member member) {
        return LoginMember.builder()
                .id(member.getId())
                .build();
    }
}

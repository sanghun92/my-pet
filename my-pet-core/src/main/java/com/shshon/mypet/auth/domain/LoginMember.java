package com.shshon.mypet.auth.domain;

import com.shshon.mypet.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginMember {

    private final Long id;

    @Builder
    public LoginMember(Long id) {
        this.id = id;
    }

    public static LoginMember from(Member member) {
        return LoginMember.builder()
                .id(member.getId())
                .build();
    }
}

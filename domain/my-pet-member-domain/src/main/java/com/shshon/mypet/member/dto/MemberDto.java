package com.shshon.mypet.member.dto;

import com.shshon.mypet.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberDto {

    private final Long id;
    private final String email;
    private final String password;
    private final String nickname;

    @Builder
    public MemberDto(Long id,
                     String email,
                     String password,
                     String nickname) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    public static MemberDto from(Member member) {
        return MemberDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .build();
    }

    public Member toMember() {
        return Member.createMember(
                this.email,
                this.password,
                this.nickname
        );
    }

    @Override
    public String toString() {
        return "MemberDto{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}

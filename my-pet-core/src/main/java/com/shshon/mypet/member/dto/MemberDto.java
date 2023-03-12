package com.shshon.mypet.member.dto;

import com.shshon.mypet.member.domain.Member;
import com.shshon.mypet.member.domain.MemberCertification;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class MemberDto {

    private final Long id;
    private final String email;
    private final String password;
    private final String nickname;
    private final LocalDate birthDay;
    private final String phoneNumber;

    public static MemberDto from(Member member) {
        return MemberDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .build();
    }

    public Member toMember() {
        return Member.builder()
                .email(this.email)
                .password(this.password)
                .nickname(this.nickname)
                .certification(MemberCertification.randomCode())
                .birthDay(this.birthDay)
                .phoneNumber(this.phoneNumber)
                .build();
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

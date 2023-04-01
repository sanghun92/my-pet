package com.shshon.mypet.member.dto;

import com.shshon.mypet.member.domain.Member;
import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record MemberDto(Long id,
                        String email,
                        String password,
                        String nickname,
                        LocalDate birthDay,
                        String phoneNumber,
                        LocalDateTime createdAt
) implements Serializable {

    public static MemberDto from(Member member) {
        return MemberDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .birthDay(member.getBirthDay())
                .phoneNumber(member.getPhoneNumber())
                .createdAt(member.getCreatedAt())
                .build();
    }

    public Member toMember() {
        return Member.builder()
                .email(this.email)
                .password(this.password)
                .nickname(this.nickname)
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
                ", birthDay=" + birthDay +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}

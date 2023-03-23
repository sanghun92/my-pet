package com.shshon.mypet.endpoint.v1.member.response;

import com.shshon.mypet.endpoint.v1.response.ResponseV1;
import com.shshon.mypet.member.dto.MemberDto;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ResponseV1
@Builder
public record MemberResponse(Long id,
                             String email,
                             String nickname,
                             LocalDate birthDay,
                             String phoneNumber,
                             LocalDateTime createdAt) {

    public static MemberResponse from(MemberDto memberDto) {
        return MemberResponse.builder()
                .id(memberDto.id())
                .email(memberDto.email())
                .nickname(memberDto.nickname())
                .birthDay(memberDto.birthDay())
                .phoneNumber(memberDto.phoneNumber())
                .createdAt(memberDto.createdAt())
                .build();
    }
}

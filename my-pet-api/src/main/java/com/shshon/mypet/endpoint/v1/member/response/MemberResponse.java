package com.shshon.mypet.endpoint.v1.member.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.shshon.mypet.auth.dto.EmailVerificationDto;
import com.shshon.mypet.endpoint.v1.response.ResponseV1;
import com.shshon.mypet.member.dto.MemberDto;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ResponseV1
@Builder
public record MemberResponse(ProfileResponse profile,
                             @JsonInclude(JsonInclude.Include.NON_NULL)
                             EmailVerificationResponse emailVerification) {

    public static MemberResponse from(MemberDto memberDto) {
        return new MemberResponse(ProfileResponse.from(memberDto), null);
    }

    public static MemberResponse from(MemberDto memberDto, EmailVerificationDto emailVerificationDto) {
        return new MemberResponse(
                ProfileResponse.from(memberDto),
                EmailVerificationResponse.from(emailVerificationDto)
        );
    }

    @Builder
    public record ProfileResponse(Long id,
                                  String email,
                                  String nickname,
                                  LocalDate birthDay,
                                  String phoneNumber,
                                  LocalDateTime createdAt) {
        private static ProfileResponse from(MemberDto memberDto) {
            return ProfileResponse.builder()
                    .id(memberDto.id())
                    .email(memberDto.email())
                    .nickname(memberDto.nickname())
                    .birthDay(memberDto.birthDay())
                    .phoneNumber(memberDto.phoneNumber())
                    .createdAt(memberDto.createdAt())
                    .build();
        }
    }

    public record EmailVerificationResponse(LocalDateTime verifiedAt,
                                            Boolean isVerified) {
        private static EmailVerificationResponse from(EmailVerificationDto emailVerificationDto) {
            return new EmailVerificationResponse(emailVerificationDto.verifiedAt(), emailVerificationDto.isVerified());
        }
    }
}

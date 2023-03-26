package com.shshon.mypet.endpoint.v1.member.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.shshon.mypet.member.dto.MemberDto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record MemberEditProfileRequest(
        @JsonProperty("nickname")
        @Size(min = 2, max = 20, message = "닉네임은 2자 이상, 20자 이하의 닉네임이어야 합니다.")
        @NotNull(message = "닉네임은 필수값 입니다.")
        String nickname,

        @JsonProperty("birthDay")
        @JsonDeserialize(using = LocalDateDeserializer.class)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate birthDay,

        @JsonProperty("phoneNumber")
        String phoneNumber
) {
    public MemberDto toMember() {
        return MemberDto.builder()
                .nickname(this.nickname)
                .birthDay(this.birthDay)
                .phoneNumber(this.phoneNumber)
                .build();
    }
}

package com.shshon.mypet.endpoint.v1.member.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shshon.mypet.member.dto.MemberDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record MemberChangePasswordRequest (

    @JsonProperty("email")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    @NotNull(message = "이메일은 필수값 입니다.")
    String email,

    @JsonProperty("password")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    @NotNull(message = "비밀번호는 필수값 입니다.")
    String password
) {
    public MemberDto toMember() {
        return MemberDto.builder()
                .email(this.email)
                .password(this.password)
                .build();
    }
}

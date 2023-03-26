package com.shshon.mypet.endpoint.v1.member.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;


public record MemberChangePasswordRequest(

        @JsonProperty("prevPassword")
        @NotNull(message = "이전 비밀번호는 필수값 입니다.")
        String prevPassword,

        @JsonProperty("newPassword")
        @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
                message = "신규 비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
        @NotNull(message = "신규 비밀번호는 필수값 입니다.")
        String newPassword
) {

}

package com.shshon.mypet.endpoint.v1.auth.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.shshon.mypet.member.domain.Member;
import com.shshon.mypet.member.dto.MemberDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import static com.shshon.mypet.endpoint.v1.auth.request.LoginMemberRequest.Const.*;

@Getter
public class LoginMemberRequest {

    @JsonProperty(FILED_EMAIL)
    @JsonPropertyDescription(DESC_EMAIL)
    @Email(message = MESSAGE_INVALID_EMAIL)
    @NotNull(message = MESSAGE_NOT_EMPTY_EMAIL)
    private final String email;

    @JsonProperty(FILED_PASSWORD)
    @JsonPropertyDescription(DESC_PASSWORD)
    @NotNull(message = MESSAGE_NOT_EMPTY_PASSWORD)
    private final String password;

    @Builder
    public LoginMemberRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static class Const {
        public static final String FILED_EMAIL = "email";
        public static final String DESC_EMAIL = "사용자 이메일 주소";
        protected static final String MESSAGE_INVALID_EMAIL = "이메일 형식에 맞지 않습니다.";
        protected static final String MESSAGE_NOT_EMPTY_EMAIL = "이메일은 필수값 입니다.";

        public static final String FILED_PASSWORD = "password";
        public static final String DESC_PASSWORD = "사용자 패스워드";
        protected static final String MESSAGE_INVALID_PASSWORD = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.";
        protected static final String MESSAGE_NOT_EMPTY_PASSWORD = "비밀번호는 필수값 입니다.";
    }
}

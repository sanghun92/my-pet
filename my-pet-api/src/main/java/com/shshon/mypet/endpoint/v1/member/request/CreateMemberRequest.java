package com.shshon.mypet.endpoint.v1.member.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.shshon.mypet.member.dto.MemberDto;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;

import static com.shshon.mypet.endpoint.v1.member.request.CreateMemberRequest.Const.*;

@Getter
public class CreateMemberRequest {
    @JsonProperty(FILED_EMAIL)
    @JsonPropertyDescription(DESC_EMAIL)
    @Email(message = MESSAGE_INVALID_EMAIL)
    @NotNull(message = MESSAGE_NOT_EMPTY_EMAIL)
    private final String email;

    @JsonProperty(FILED_PASSWORD)
    @JsonPropertyDescription(DESC_PASSWORD)
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}", message = MESSAGE_INVALID_PASSWORD)
    @NotNull(message = MESSAGE_NOT_EMPTY_PASSWORD)
    private final String password;

    @JsonProperty(FILED_NICKNAME)
    @JsonPropertyDescription(DESC_NICKNAME)
    @Size(min = 2, max = 20, message = MESSAGE_INVALID_NICKNAME)
    @NotNull(message = MESSAGE_NOT_EMPTY_NICKNAME)
    private final String nickname;

    @Builder
    public CreateMemberRequest(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    public MemberDto toMember() {
        return MemberDto.builder()
                .email(this.email)
                .password(this.password)
                .nickname(this.nickname)
                .build();
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

        public static final String FILED_NICKNAME = "nickname";
        public static final String DESC_NICKNAME = "2자 이상, 20자 이하의 사용자 닉네임";
        protected static final String MESSAGE_INVALID_NICKNAME = "닉네임은 2자 이상, 20자 이하의 닉네임이어야 합니다.";
        protected static final String MESSAGE_NOT_EMPTY_NICKNAME = "닉네임은 필수값 입니다.";

    }
}

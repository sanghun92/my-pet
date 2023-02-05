package com.shshon.mypet.endpoint.v1.member.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.shshon.mypet.member.dto.MemberDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateMemberRequest {

    private static final String DESC_EMAIL = "사용자 이메일 주소";
    private static final String MESSAGE_INVALID_EMAIL = "이메일 형식에 맞지 않습니다.";
    private static final String MESSAGE_NOT_EMPTY_EMAIL = "이메일은 필수값 입니다.";
    private static final String DESC_PASSWORD = "사용자 패스워드";
    private static final String MESSAGE_INVALID_PASSWORD = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.";
    private static final String DESC_NICKNAME = "4자 이상, 20자 이하의 사용자 닉네임";
    private static final String MESSAGE_INVALID_NICKNAME = "닉네임은 4자 이상, 20자 이하의 닉네임이어야 합니다.";
    private static final String MESSAGE_NOT_EMPTY_NICKNAME = "닉네임은 필수값 입니다.";

    @JsonProperty
    @JsonPropertyDescription(DESC_EMAIL)
    @Email(message = MESSAGE_INVALID_EMAIL)
    @NotEmpty(message = MESSAGE_NOT_EMPTY_EMAIL)
    private final String email;

    @JsonProperty
    @JsonPropertyDescription(DESC_PASSWORD)
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}", message = MESSAGE_INVALID_PASSWORD)
    private final String password;

    @JsonProperty
    @JsonPropertyDescription(DESC_NICKNAME)
    @Size(min = 4, max = 20, message = MESSAGE_INVALID_NICKNAME)
    @NotEmpty(message = MESSAGE_NOT_EMPTY_NICKNAME)
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
}

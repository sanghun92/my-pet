package com.shshon.mypet.endpoint.v1.auth.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonDeserialize(builder = LoginMemberRequest.LoginMemberRequestBuilder.class)
public class LoginMemberRequest {

    @JsonProperty("email")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    @NotNull(message = "이메일은 필수값 입니다.")
    private final String email;

    @JsonProperty("password")
    @NotNull(message = "비밀번호는 필수값 입니다.")
    private final String password;
}

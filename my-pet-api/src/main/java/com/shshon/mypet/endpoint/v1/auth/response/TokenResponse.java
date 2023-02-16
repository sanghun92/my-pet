package com.shshon.mypet.endpoint.v1.auth.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.shshon.mypet.auth.dto.TokenDto;
import lombok.Builder;
import lombok.Getter;

import static com.shshon.mypet.endpoint.v1.auth.response.TokenResponse.Const.DESC_TOKEN;
import static com.shshon.mypet.endpoint.v1.auth.response.TokenResponse.Const.FILED_TOKEN;

@Getter
public class TokenResponse {

    @JsonProperty(FILED_TOKEN)
    @JsonPropertyDescription(DESC_TOKEN)
    private final String token;

    @Builder
    public TokenResponse(String token) {
        this.token = token;
    }

    public static TokenResponse of(TokenDto tokenDto) {
        return TokenResponse.builder()
                .token(tokenDto.getToken())
                .build();
    }

    public static class Const {
        public static final String FILED_TOKEN = "token";
        public static final String DESC_TOKEN = "사용자 인증 토큰";
    }

}

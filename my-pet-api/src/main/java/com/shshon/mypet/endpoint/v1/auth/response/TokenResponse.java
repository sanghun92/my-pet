package com.shshon.mypet.endpoint.v1.auth.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shshon.mypet.auth.dto.TokenDto;
import com.shshon.mypet.endpoint.v1.ResponseV1;
import lombok.Builder;
import lombok.Getter;

@ResponseV1
@Getter
public class TokenResponse {

    @JsonProperty("token")
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

}

package com.shshon.mypet.endpoint.v1.auth.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shshon.mypet.auth.dto.TokenDto;
import com.shshon.mypet.endpoint.v1.response.ResponseV1;
import lombok.Builder;

@ResponseV1
public record TokenResponse(
        @JsonProperty("token")
        String token
) {

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

package com.shshon.mypet.endpoint.v1.auth.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shshon.mypet.auth.dto.TokenDto;
import com.shshon.mypet.endpoint.v1.response.ResponseV1;
import lombok.Builder;

@ResponseV1
public record TokenResponse(
        @JsonProperty("accessToken")
        String token
) {

}

package com.shshon.mypet.endpoint.v1.auth.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record TokenReIssueRequest(

        @JsonProperty("refreshToken")
        @NotNull(message = "Refresh Token은 필수값 입니다.")
        String refreshToken
) {
}

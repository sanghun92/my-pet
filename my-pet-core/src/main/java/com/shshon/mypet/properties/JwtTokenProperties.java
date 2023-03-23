package com.shshon.mypet.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@ConfigurationProperties("security.jwt")
public record JwtTokenProperties(@NestedConfigurationProperty
                                 AccessToken accessToken,

                                 @NestedConfigurationProperty
                                 RefreshToken refreshToken) {

    public record AccessToken(String secretKey, Long validityInSeconds) {
    }

    public record RefreshToken(Long validityInSeconds) {
    }
}

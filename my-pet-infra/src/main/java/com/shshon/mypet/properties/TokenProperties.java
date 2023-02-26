package com.shshon.mypet.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("security.jwt.token")
public class TokenProperties {

    private String secretKey;
    private Long validityInMilliseconds;
}

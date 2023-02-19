package com.shshon.mypet.auth.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("security")
public class SecurityProperties {

    private Jwt jwt;

    @Getter
    @Setter
    public static class Jwt {
        private Token token;
    }

    @Getter
    @Setter
    public static class Token {
        private String secretKey;
        private Long validityInMilliseconds;
    }
}

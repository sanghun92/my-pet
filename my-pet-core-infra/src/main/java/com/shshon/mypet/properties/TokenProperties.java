package com.shshon.mypet.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("security.jwt.token")
public record TokenProperties(String secretKey,
                              Long validityInMilliseconds) { }

package com.shshon.mypet.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "spring.mail")
public record MailProperties(String host,
                             Integer port,
                             String username,
                             String password,
                             Map<String, String> properties) { }

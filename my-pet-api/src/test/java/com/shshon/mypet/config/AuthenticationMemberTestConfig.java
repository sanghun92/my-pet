package com.shshon.mypet.config;

import com.shshon.mypet.advice.requestDecorator.AuthenticationMemberArgumentResolver;
import com.shshon.mypet.stub.auth.AuthQueryServiceStub;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@TestConfiguration
public class AuthenticationMemberTestConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(createAuthenticationPrincipalArgumentResolver());
    }

    @Bean
    public AuthenticationMemberArgumentResolver createAuthenticationPrincipalArgumentResolver() {
        return new AuthenticationMemberArgumentResolver(new AuthQueryServiceStub());
    }
}

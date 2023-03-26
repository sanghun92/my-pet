package com.shshon.mypet.config;

import com.shshon.mypet.advice.clientDecorator.RequestClientArgumentResolver;
import com.shshon.mypet.advice.requestDecorator.AuthenticationMemberArgumentResolver;
import com.shshon.mypet.auth.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@Profile("!test")
@RequiredArgsConstructor
public class ArgumentResolverConfig implements WebMvcConfigurer {

    private final TokenService tokenService;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(createAuthenticationPrincipalArgumentResolver());
        argumentResolvers.add(createRequestClientArgumentResolver());
    }

    @Bean
    public AuthenticationMemberArgumentResolver createAuthenticationPrincipalArgumentResolver() {
        return new AuthenticationMemberArgumentResolver(tokenService);
    }

    @Bean
    public RequestClientArgumentResolver createRequestClientArgumentResolver() {
        return new RequestClientArgumentResolver();
    }
}

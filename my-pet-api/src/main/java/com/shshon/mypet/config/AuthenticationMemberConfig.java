package com.shshon.mypet.config;

import com.shshon.mypet.advice.requestDecorator.AuthenticationMemberArgumentResolver;
import com.shshon.mypet.auth.service.AuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@Profile("!test")
public class AuthenticationMemberConfig implements WebMvcConfigurer {

    private final AuthService authService;

    public AuthenticationMemberConfig(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(createAuthenticationPrincipalArgumentResolver());
    }

    @Bean
    public AuthenticationMemberArgumentResolver createAuthenticationPrincipalArgumentResolver() {
        return new AuthenticationMemberArgumentResolver(authService);
    }
}

package com.shshon.mypet.config;

import com.shshon.mypet.advice.requestDecorator.AuthenticationPrincipalArgumentResolver;
import com.shshon.mypet.auth.domain.LoginMember;
import com.shshon.mypet.auth.service.AuthService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@TestConfiguration
public class AuthenticationPrincipalTestConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(createAuthenticationPrincipalArgumentResolver());
    }

    @Bean
    public AuthenticationPrincipalArgumentResolver createAuthenticationPrincipalArgumentResolver() {
        return new AuthenticationPrincipalArgumentResolver(new AuthServiceStub());
    }

    private static class AuthServiceStub extends AuthService {

        public AuthServiceStub() {
            super(null, null);
        }

        @Override
        public LoginMember findMemberByToken(String token) {
            return LoginMember.builder()
                    .id(1L)
                    .build();
        }
    }
}

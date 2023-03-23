package com.shshon.mypet.config;

import com.shshon.mypet.advice.requestDecorator.AuthenticationMemberArgumentResolver;
import com.shshon.mypet.auth.application.AuthService;
import com.shshon.mypet.auth.domain.LoginMember;
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
        return new AuthenticationMemberArgumentResolver(new AuthServiceStub());
    }

    private static class AuthServiceStub extends AuthService {

        public AuthServiceStub() {
            super(null, null, null, null);
        }

        @Override
        public LoginMember findMemberByToken(String token) {
            return LoginMember.builder()
                    .id(1L)
                    .build();
        }
    }
}

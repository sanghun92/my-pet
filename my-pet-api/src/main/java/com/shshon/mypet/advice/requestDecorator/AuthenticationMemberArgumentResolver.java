package com.shshon.mypet.advice.requestDecorator;

import com.shshon.mypet.auth.domain.AuthenticationMember;
import com.shshon.mypet.auth.service.TokenService;
import com.shshon.mypet.endpoint.v1.auth.AuthorizationExtractor;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AuthenticationMemberArgumentResolver implements HandlerMethodArgumentResolver {

    private final TokenService tokenService;

    public AuthenticationMemberArgumentResolver(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationMember.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String email = AuthorizationExtractor.extract(webRequest.getNativeRequest(HttpServletRequest.class));
        return tokenService.findLoginMemberBy(email);
    }
}

package com.shshon.mypet.advice.responseDecorator;

import com.shshon.mypet.endpoint.v1.ApiResponseV1;
import com.shshon.mypet.endpoint.v1.ResponseV1;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Method;
import java.util.Arrays;

@RestControllerAdvice
public class V1ResponseDecorator implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        Method returnTypeMethod = returnType.getMethod();
        if(returnTypeMethod == null) {
            return false;
        }

        Class<?> methodReturnType = returnTypeMethod.getReturnType();
        return methodReturnType.getDeclaredAnnotation(ResponseV1.class) != null;
    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {
        return ApiResponseV1.ok(body);
    }
}

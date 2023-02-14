package com.shshon.mypet.advice.errorHandler;

import com.shshon.mypet.endpoint.v1.ErrorResponseV1;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface ApiV1ExceptionHandler {

    default boolean support(Exception exception) {
        return false;
    }

    default String getRequestURI(HttpServletRequest request) {
        String queryString = request.getQueryString();
        return queryString != null
                ? request.getRequestURI() + "?" + queryString
                : request.getRequestURI();
    }

    ResponseEntity<ErrorResponseV1> onException(HttpServletRequest request, Exception exception);
}

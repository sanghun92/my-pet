package com.shshon.mypet.advice.responseDecorator;

import com.shshon.mypet.advice.errorHandler.ApiV1ExceptionHandler;
import com.shshon.mypet.endpoint.v1.ApiResponseV1;
import com.shshon.mypet.endpoint.v1.ErrorResponseV1;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestController
@RestControllerAdvice
@Slf4j
public class V1ExceptionResponseDecorator implements ErrorController {

    private static final String SERVLET_EXCEPTION = "javax.servlet.error.exception";

    private final List<ApiV1ExceptionHandler> exceptionHandlers;

    @Autowired
    public V1ExceptionResponseDecorator(List<ApiV1ExceptionHandler> exceptionHandlers) {
        this.exceptionHandlers = exceptionHandlers;
    }

    @RequestMapping("/error")
    public ResponseEntity<ErrorResponseV1> handleError(HttpServletRequest request) {
        Object exception = request.getAttribute(SERVLET_EXCEPTION);
        if(exception instanceof Exception) {
            return onError(request, (Exception) exception);
        }

        log.error("unhandled servlet exception : {}?{}", request.getRequestURI(), request.getQueryString());
        return new ResponseEntity<>(ApiResponseV1.serverError("unhandled exception"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseV1> onError(HttpServletRequest request, Exception exception) {
        return this.exceptionHandlers.stream()
                .filter(handler -> handler.support(exception))
                .findFirst()
                .orElseThrow(IllegalAccessError::new)
                .onException(request, exception);
    }
}

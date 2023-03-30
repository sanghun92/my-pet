package com.shshon.mypet.advice.responseDecorator;

import com.shshon.mypet.advice.errorHandler.ApiV1ExceptionHandler;
import com.shshon.mypet.endpoint.v1.response.ApiResponseV1;
import com.shshon.mypet.endpoint.v1.response.ErrorResponseV1;
import com.shshon.mypet.member.exception.AuthorizationException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;

@RestController
@RestControllerAdvice
@RequestMapping("/error")
@Slf4j
public class V1ExceptionResponseDecorator implements ErrorController {

    private static final String SERVLET_EXCEPTION = "javax.servlet.error.exception";

    private final List<ApiV1ExceptionHandler> exceptionHandlers;

    @Autowired
    public V1ExceptionResponseDecorator(List<ApiV1ExceptionHandler> exceptionHandlers) {
        this.exceptionHandlers = exceptionHandlers;
    }

    @GetMapping
    public ResponseEntity<ErrorResponseV1> handleError(HttpServletRequest request) {
        Object exception = request.getAttribute(SERVLET_EXCEPTION);
        if (exception instanceof Exception) {
            return handleException(request, (Exception) exception);
        }

        HttpStatus status = ApiV1ExceptionHandler.getStatus(request);
        ApiV1ExceptionHandler.requestLog(log, request, status, null);
        return new ResponseEntity<>(ApiResponseV1.serverError("unhandled exception"), status);
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<ErrorResponseV1> handleAuthorizationException(HttpServletRequest request,
                                                                        AuthorizationException ex) {
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        ApiV1ExceptionHandler.requestLog(log, request, httpStatus, ex);
        ErrorResponseV1 response = ApiResponseV1.unauthorized(ex.getMessage());
        return new ResponseEntity<>(response, httpStatus);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponseV1> handleAuthenticationException(HttpServletRequest request,
                                                                         AuthenticationException ex) {
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        ApiV1ExceptionHandler.requestLog(log, request, httpStatus, ex);
        ErrorResponseV1 response = ApiResponseV1.unauthorized(ex.getMessage());
        return new ResponseEntity<>(response, httpStatus);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponseV1> handleHttpMediaTypeNotSupportedException(HttpServletRequest request,
                                                                                    HttpMediaTypeNotSupportedException ex) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ApiV1ExceptionHandler.requestLog(log, request, httpStatus, ex);
        ErrorResponseV1 response = ApiResponseV1.clientError(ex.getMessage(), httpStatus);
        return new ResponseEntity<>(response, httpStatus);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponseV1> handleHttpRequestMethodNotSupportedException(HttpServletRequest request,
                                                                                        HttpRequestMethodNotSupportedException ex) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ApiV1ExceptionHandler.requestLog(log, request, httpStatus, ex);
        ErrorResponseV1 response = ApiResponseV1.clientError(ex.getMessage(), httpStatus);
        return new ResponseEntity<>(response, httpStatus);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponseV1> handleNoHandlerFoundException(HttpServletRequest request,
                                                                         NoHandlerFoundException ex) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ApiV1ExceptionHandler.requestLog(log, request, httpStatus, ex);
        ErrorResponseV1 response = ApiResponseV1.clientError("Not Found", httpStatus);
        return new ResponseEntity<>(response, httpStatus);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseV1> handleException(HttpServletRequest request, Exception exception) {
        return this.exceptionHandlers.stream()
                .filter(handler -> handler.support(exception))
                .findFirst()
                .orElseThrow(IllegalAccessError::new)
                .onException(request, exception);
    }
}

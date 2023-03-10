package com.shshon.mypet.advice.responseDecorator;

import com.shshon.mypet.advice.errorHandler.ApiV1ExceptionHandler;
import com.shshon.mypet.endpoint.v1.ApiResponseV1;
import com.shshon.mypet.endpoint.v1.ErrorResponseV1;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.*;

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
        if(exception instanceof Exception) {
            return handleException(request, (Exception) exception);
        }

        HttpStatus status = ApiV1ExceptionHandler.getStatus(request);
        return new ResponseEntity<>(ApiResponseV1.serverError("unhandled exception"), status);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponseV1> handleAuthenticationException(HttpServletRequest request,
                                                                         AuthenticationException ex) {
        ApiV1ExceptionHandler.requestLog(log, request, ex);
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        ErrorResponseV1 response = ApiResponseV1.unauthorized(ex.getMessage());
        return new ResponseEntity<>(response, httpStatus);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponseV1> handleHttpMediaTypeNotSupportedException(HttpServletRequest request,
                                                                                    HttpMediaTypeNotSupportedException ex) {
        ApiV1ExceptionHandler.requestLog(log, request, ex);
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErrorResponseV1 response = ApiResponseV1.clientError(ex.getMessage());
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

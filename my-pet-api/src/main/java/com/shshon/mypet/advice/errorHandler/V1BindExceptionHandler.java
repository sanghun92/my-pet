package com.shshon.mypet.advice.errorHandler;

import com.shshon.mypet.endpoint.v1.ApiResponseV1;
import com.shshon.mypet.endpoint.v1.ErrorResponseV1;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class V1BindExceptionHandler implements ApiV1ExceptionHandler {

    @Override
    public boolean support(Exception exception) {
        return exception instanceof BindException;
    }

    @Override
    public ResponseEntity<ErrorResponseV1> onException(HttpServletRequest request, Exception exception) {
        log.error("Bind exception: {}", getRequestURI(request), exception);

        BindException bindEx = (BindException) exception;
        List<String> messages = bindEx.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> "[" + fieldError.getField() + "] " + fieldError.getDefaultMessage())
                .collect(Collectors.toList());

        return new ResponseEntity<>(ApiResponseV1.clientError(messages), HttpStatus.BAD_REQUEST);
    }
}

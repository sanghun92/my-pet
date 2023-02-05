package com.shshon.mypet.advice.responseDecorator;

import com.shshon.mypet.endpoint.v1.ApiResponseV1;
import com.shshon.mypet.endpoint.v1.ErrorResponseV1;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class V1ExceptionResponseDecorator {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    protected ApiResponseV1<List<ErrorResponseV1.Body>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> messages = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> "[" + fieldError.getField() + "] " + fieldError.getDefaultMessage())
                .collect(Collectors.toList());

        return ApiResponseV1.clientError(messages);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    protected ApiResponseV1<List<ErrorResponseV1.Body>> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ApiResponseV1.clientError(ex.getMessage());
    }
}

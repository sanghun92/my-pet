package com.shshon.mypet.advice.errorHandler;

import com.shshon.mypet.endpoint.v1.response.ApiResponseV1;
import com.shshon.mypet.endpoint.v1.response.ErrorBody;
import com.shshon.mypet.endpoint.v1.response.ErrorResponseV1;
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

    public static final String BIND_ERROR_MESSAGE = "잘못 된 요청입니다.";

    @Override
    public boolean support(Exception exception) {
        return exception instanceof BindException;
    }

    @Override
    public ResponseEntity<ErrorResponseV1> onException(HttpServletRequest request, Exception exception) {
        ApiV1ExceptionHandler.requestLog(log, request, HttpStatus.BAD_REQUEST, exception);
        BindException bindEx = (BindException) exception;
        List<ErrorBody.ErrorField> errorFields = bindEx.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> new ErrorBody.ErrorField(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(ApiResponseV1.clientError(BIND_ERROR_MESSAGE, errorFields), HttpStatus.BAD_REQUEST);
    }
}

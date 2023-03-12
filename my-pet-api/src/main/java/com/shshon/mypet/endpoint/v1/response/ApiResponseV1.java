package com.shshon.mypet.endpoint.v1.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public abstract class ApiResponseV1<T> {

    private final LocalDateTime timestamp = LocalDateTime.now();
    private final boolean success;
    private final T data;

    public ApiResponseV1(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public static <T> ApiResponseV1<T> ok(T body) {
        return OkResponseV1.of(body);
    }

    public static ErrorResponseV1 clientError(String message) {
        return ErrorResponseV1.from(HttpStatus.BAD_REQUEST.value(), message);
    }

    public static ErrorResponseV1 clientError(List<String> message) {
        return ErrorResponseV1.from(HttpStatus.BAD_REQUEST.value(), message);
    }

    public static ErrorResponseV1 unauthorized(String message) {
        return ErrorResponseV1.from(HttpStatus.UNAUTHORIZED.value(), message);
    }

    public static ErrorResponseV1 serverError(String message) {
        return ErrorResponseV1.from(HttpStatus.INTERNAL_SERVER_ERROR.value(), message);
    }
}

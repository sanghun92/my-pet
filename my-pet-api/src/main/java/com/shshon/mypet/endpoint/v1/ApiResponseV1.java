package com.shshon.mypet.endpoint.v1;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public abstract class ApiResponseV1<T> {

    private final LocalDateTime timestamp = LocalDateTime.now();
    private final Type type;
    private final T body;

    public ApiResponseV1(Type type, T body) {
        this.type = type;
        this.body = body;
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

    public static ErrorResponseV1 serverError(String message) {
        return ErrorResponseV1.from(HttpStatus.INTERNAL_SERVER_ERROR.value(), message);
    }

    protected enum Type {
        OK("OK"),
        ERROR("ERROR");

        private final String value;

        Type(String value) {
            this.value = value;
        }

        public String value() {
            return this.value;
        }
    }
}

package com.shshon.mypet.endpoint.v1;

import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ErrorResponseV1 extends ApiResponseV1<List<ErrorResponseV1.Body>> {

    private ErrorResponseV1(int statusCode, List<String> messages) {
        super(Type.ERROR, toBody(statusCode, messages));
    }

    private static List<Body> toBody(int statusCode, List<String> messages) {
        return messages.stream()
                .map(message -> new Body(statusCode, message))
                .collect(Collectors.toList());
    }

    public static ErrorResponseV1 from(int statusCode, String message) {
        return new ErrorResponseV1(statusCode, List.of(message));
    }

    public static ErrorResponseV1 from(int statusCode, List<String> message) {
        return new ErrorResponseV1(statusCode, message);
    }

    public record Body(int code, String message) { }
}

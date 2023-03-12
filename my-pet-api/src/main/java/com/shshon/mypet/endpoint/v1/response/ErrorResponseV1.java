package com.shshon.mypet.endpoint.v1.response;

import lombok.Getter;

import java.util.List;

@Getter
public class ErrorResponseV1 extends ApiResponseV1<ErrorBody> {

    private ErrorResponseV1(int statusCode, List<String> messages) {
        super(false, ErrorBody.of(statusCode, messages));
    }


    public static ErrorResponseV1 from(int statusCode, String message) {
        return new ErrorResponseV1(statusCode, List.of(message));
    }

    public static ErrorResponseV1 from(int statusCode, List<String> message) {
        return new ErrorResponseV1(statusCode, message);
    }
}

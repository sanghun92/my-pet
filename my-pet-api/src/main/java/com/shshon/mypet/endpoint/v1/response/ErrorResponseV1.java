package com.shshon.mypet.endpoint.v1.response;

import lombok.Getter;

import java.util.List;

@Getter
public class ErrorResponseV1 extends ApiResponseV1<ErrorBody> {

    private ErrorResponseV1(ErrorBody errorBody) {
        super(false, errorBody);
    }


    public static ErrorResponseV1 from(int code, String message) {
        return new ErrorResponseV1(new ErrorBody(code, message, null));
    }

    public static ErrorResponseV1 from(int code, String message, List<ErrorBody.ErrorField> errorFields) {
        return new ErrorResponseV1(new ErrorBody(code, message, errorFields));
    }
}

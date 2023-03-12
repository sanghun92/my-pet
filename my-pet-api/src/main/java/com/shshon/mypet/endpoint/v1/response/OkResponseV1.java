package com.shshon.mypet.endpoint.v1.response;

import lombok.Getter;

@Getter
public class OkResponseV1<T> extends ApiResponseV1<T> {

    protected OkResponseV1() {
        super(true, null);
    }

    protected OkResponseV1(T body) {
        super(true, body);
    }

    public static <T> ApiResponseV1<T> of(T body) {
        return new OkResponseV1<>(body);
    }
}

package com.shshon.mypet.endpoint.v1;

import lombok.Getter;

@Getter
public class OkResponseV1<T> extends ApiResponseV1<T> {

    private OkResponseV1(T body) {
        super(Type.OK, body);
    }

    public static <T> ApiResponseV1<T> of(T body) {
        return new OkResponseV1<>(body);
    }
}

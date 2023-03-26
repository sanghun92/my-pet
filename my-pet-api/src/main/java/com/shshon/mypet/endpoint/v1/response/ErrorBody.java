package com.shshon.mypet.endpoint.v1.response;

import java.util.List;

public record ErrorBody(int code, String message, List<ErrorField> errorFields) {

    public record ErrorField(String field, String message) {

    }
}

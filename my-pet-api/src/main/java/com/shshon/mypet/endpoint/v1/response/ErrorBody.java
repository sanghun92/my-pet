package com.shshon.mypet.endpoint.v1.response;

import java.util.List;
import java.util.stream.Collectors;

public record ErrorBody(int code, List<ErrorMessage> messages) {
    public static ErrorBody of(int statusCode, List<String> messages) {
        return new ErrorBody(statusCode, ErrorMessage.of(messages));
    }

    private record ErrorMessage(String message) {

        public static List<ErrorMessage> of(List<String> messages) {
            return messages.stream()
                    .map(ErrorMessage::new)
                    .collect(Collectors.toList());
        }
    }
}

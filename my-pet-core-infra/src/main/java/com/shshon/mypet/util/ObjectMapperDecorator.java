package com.shshon.mypet.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ObjectMapperDecorator {

    private final ObjectMapper objectMapper;

    public <T> Optional<T> convertValue(Object object, Class<T> toValueType) {
        if (object == null) {
            return Optional.empty();
        }

        return Optional.of(objectMapper.convertValue(object, toValueType));
    }

    public <T> Optional<T> convertValue(Object object, TypeReference<T> toValueTypeRef) {
        if (object == null) {
            return Optional.empty();
        }

        return Optional.of(objectMapper.convertValue(object, toValueTypeRef));
    }
}

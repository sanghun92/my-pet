package com.shshon.mypet.common.exception;

public class EntityNotFoundException extends ClientException {

    private static final String DEFAULT_MESSAGE = "해당 엔티티를 찾을 수 없습니다.";

    public EntityNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}

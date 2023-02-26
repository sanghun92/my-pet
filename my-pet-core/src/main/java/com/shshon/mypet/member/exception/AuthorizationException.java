package com.shshon.mypet.member.exception;

import com.shshon.mypet.common.exception.ClientException;

public class AuthorizationException extends ClientException {

    private static final String DEFAULT_MESSAGE = "인증 정보가 유효하지 않습니다.";
    
    public AuthorizationException() {
        super(DEFAULT_MESSAGE);
    }
}

package com.shshon.mypet.auth.exception;

import com.shshon.mypet.common.exception.ClientException;

public class AlreadyVerifiedEmailException extends ClientException {

    private static final String ERROR_ALREADY_VERIFIED = "이미 인증 된 회원입니다.";

    public AlreadyVerifiedEmailException() {
        super(ERROR_ALREADY_VERIFIED);
    }
}

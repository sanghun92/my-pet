package com.shshon.mypet.auth.exception;

import com.shshon.mypet.common.exception.ClientException;

public class AlreadySentVerificationCode extends ClientException {

    public AlreadySentVerificationCode(String message) {
        super(message);
    }
}

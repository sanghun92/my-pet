package com.shshon.mypet.member.exception;

import com.shshon.mypet.common.exception.ClientException;

public class AlreadyCertificatedMemberException extends ClientException {

    private static final String ERROR_ALREADY_CERTIFICATED = "이미 인증 된 회원입니다.";

    public AlreadyCertificatedMemberException() {
        super(ERROR_ALREADY_CERTIFICATED);
    }
}

package com.shshon.mypet.member.exception;

import com.shshon.mypet.common.exception.ClientException;

public class InValidMemberException extends ClientException {

    private static final String ERROR_DUPLICATED_EMAIL = "이미 가입 된 이메일 주소입니다.";

    public InValidMemberException(String message) {
        super(message);
    }

    public static InValidMemberException duplicatedEmail() {
        return new InValidMemberException(ERROR_DUPLICATED_EMAIL);
    }
}

package com.shshon.mypet.member.exception;

import com.shshon.mypet.common.exception.ServerException;

public class InValidCreateMemberEventException extends ServerException {

    private static final String NOT_NULL_CERTIFICATION_CODE = "인증 코드는 필수값입니다.";
    private static final String NOT_NULL_EMAIL = "이메일은 필수값입니다.";
    private static final String NOT_NULL_NICK_NAME = "닉네임은 필수값입니다.";

    public InValidCreateMemberEventException(String message) {
        super(message);
    }

    public static InValidCreateMemberEventException emptyCertificationCode() {
        return new InValidCreateMemberEventException(NOT_NULL_CERTIFICATION_CODE);
    }

    public static InValidCreateMemberEventException emptyEmail() {
        return new InValidCreateMemberEventException(NOT_NULL_EMAIL);
    }

    public static InValidCreateMemberEventException emptyNickName() {
        return new InValidCreateMemberEventException(NOT_NULL_NICK_NAME);
    }
}

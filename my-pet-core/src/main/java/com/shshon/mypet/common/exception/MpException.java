package com.shshon.mypet.common.exception;

public class MpException extends RuntimeException {

    public MpException() {
    }

    public MpException(String message) {
        super(message);
    }

    public MpException(Throwable cause) {
        super(cause);
    }

    public MpException(String message, Throwable cause) {
        super(message, cause);
    }
}

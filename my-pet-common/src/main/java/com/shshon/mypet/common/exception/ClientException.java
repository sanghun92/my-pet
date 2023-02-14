package com.shshon.mypet.common.exception;

public class ClientException extends MpException {

    public ClientException() {
    }

    public ClientException(String message) {
        super(message);
    }

    public ClientException(Throwable cause) {
        super(cause);
    }

    public ClientException(String message, Throwable cause) {
        super(message, cause);
    }
}

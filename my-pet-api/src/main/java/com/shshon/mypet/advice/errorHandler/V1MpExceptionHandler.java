package com.shshon.mypet.advice.errorHandler;

import com.shshon.mypet.common.exception.ClientException;
import com.shshon.mypet.common.exception.MpException;
import com.shshon.mypet.common.exception.ServerException;
import com.shshon.mypet.endpoint.v1.response.ErrorResponseV1;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class V1MpExceptionHandler implements ApiV1ExceptionHandler {

    @Override
    public boolean support(Exception exception) {
        return exception instanceof MpException;
    }

    @Override
    public ResponseEntity<ErrorResponseV1> onException(HttpServletRequest request, Exception exception) {
        if(exception instanceof ClientException) {
            return onClientException(request, (ClientException) exception);
        } else if(exception instanceof ServerException) {
            return onServerException(request, (ServerException) exception);
        } else {
            return null;
        }
    }

    private ResponseEntity<ErrorResponseV1> onClientException(HttpServletRequest request, ClientException ex) {
        ApiV1ExceptionHandler.requestLog(log, request, HttpStatus.BAD_REQUEST, ex);
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErrorResponseV1 response = ErrorResponseV1.from(httpStatus.value(), ex.getMessage());
        return new ResponseEntity<>(response, httpStatus);
    }

    private ResponseEntity<ErrorResponseV1> onServerException(HttpServletRequest request, ServerException ex) {
        ApiV1ExceptionHandler.requestLog(log, request, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponseV1 response = ErrorResponseV1.from(httpStatus.value(), ex.getMessage());
        return new ResponseEntity<>(response, httpStatus);
    }
}

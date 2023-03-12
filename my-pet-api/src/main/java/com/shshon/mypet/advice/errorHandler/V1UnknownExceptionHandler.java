package com.shshon.mypet.advice.errorHandler;

import com.shshon.mypet.endpoint.v1.response.ApiResponseV1;
import com.shshon.mypet.endpoint.v1.response.ErrorResponseV1;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Order
public class V1UnknownExceptionHandler implements ApiV1ExceptionHandler {

    @Override
    public boolean support(Exception exception) {
        return exception != null;
    }

    @Override
    public ResponseEntity<ErrorResponseV1> onException(HttpServletRequest request, Exception ex) {
        ApiV1ExceptionHandler.requestLog(log, request, ex);
        HttpStatus httpStatus = ApiV1ExceptionHandler.getStatus(request);
        ErrorResponseV1 response = ApiResponseV1.serverError("unknown");
        return new ResponseEntity<>(response, httpStatus);
    }
}

package com.shshon.mypet.advice.errorHandler;

import com.shshon.mypet.endpoint.v1.ApiResponseV1;
import com.shshon.mypet.endpoint.v1.ErrorResponseV1;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class V1UnknownExceptionHandler implements ApiV1ExceptionHandler {

    @Override
    public boolean support(Exception exception) {
        return exception != null;
    }

    @Override
    public ResponseEntity<ErrorResponseV1> onException(HttpServletRequest request, Exception ex) {
        log.error("Unknown exception: [{}] {}",
                request.getMethod(),
                getRequestURI(request),
                ex);

        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponseV1 response = ApiResponseV1.serverError("unknown");
        return new ResponseEntity<>(response, httpStatus);
    }
}

package com.shshon.mypet.advice.errorHandler;

import com.shshon.mypet.endpoint.v1.response.ErrorResponseV1;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface ApiV1ExceptionHandler {

    static void requestLog(Logger logger,
                           HttpServletRequest request,
                           Exception exception) {
        requestLog(logger, request, getStatus(request), exception);
    }

    static void requestLog(Logger logger,
                           HttpServletRequest request,
                           HttpStatus status,
                           Exception exception) {
        logger.error("[{}] uri : {}, status : {}, exception: {}",
                request.getMethod(),
                getRequestURI(request),
                status,
                exception == null ? "" : exception.getClass().getName(),
                exception);
    }

    static String getRequestURI(HttpServletRequest request) {
        String queryString = request.getQueryString();
        return queryString != null
                ? request.getRequestURI() + "?" + queryString
                : request.getRequestURI();
    }

    static HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        try {
            return HttpStatus.valueOf(statusCode);
        }
        catch (Exception ex) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    default boolean support(Exception exception) {
        return false;
    }

    ResponseEntity<ErrorResponseV1> onException(HttpServletRequest request, Exception exception);
}

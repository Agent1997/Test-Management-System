package com.agent1997.tms.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.net.ConnectException;
import java.util.UUID;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConnectException.class)
    public ResponseEntity<GenericErrorResponse> handleConnectException(ConnectException ex) {
        String logId = UUID.randomUUID().toString();
        log.error("Log id: {}. Database connection error: {}", logId, ex.getMessage(), ex);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new GenericErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                        "Service unavailable. Please check logs.",
                        logId));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<BadRequestErrorResponse> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException ex) {
        String logId = UUID.randomUUID().toString();

        log.error("Log id: {}. Unsupported request method error: {}.", logId, ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new BadRequestErrorResponse(logId));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<BadRequestErrorResponse> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        String logId = UUID.randomUUID().toString();
        log.error("Log id: {}. No handler found for {}.", logId, ex.getRequestURL());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new BadRequestErrorResponse(logId));
    }

//    TODO add handler for database exception
}

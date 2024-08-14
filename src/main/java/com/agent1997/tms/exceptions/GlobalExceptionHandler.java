package com.agent1997.tms.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
                .body(new GenericErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Database connection failed. " +
                        "Please check if the external service is up and reachable.", logId));
    }
}

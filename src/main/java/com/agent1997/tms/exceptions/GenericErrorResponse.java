package com.agent1997.tms.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class GenericErrorResponse {
    private final LocalDateTime timestamp;
    private final Integer statusCode;
    private final String reason;
    private final String logId;
    private final String message;

    public GenericErrorResponse(HttpStatus httpStatus, String message, String logId){
        this.statusCode = httpStatus.value();
        this.reason = httpStatus.getReasonPhrase();
        this.message = message;
        this.logId = logId;
        this.timestamp = LocalDateTime.now();
    }
}

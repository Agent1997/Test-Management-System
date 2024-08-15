package com.agent1997.tms.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class BadRequestErrorResponse{
    private final LocalDateTime timestamp ;
    private final Integer statusCode;
    private final String reason;
    private final String message;
    private final String logId;

    public BadRequestErrorResponse(String logId) {
        this.timestamp = LocalDateTime.now();
        this.statusCode = HttpStatus.BAD_REQUEST.value();
        this.reason = HttpStatus.BAD_REQUEST.getReasonPhrase();
        this.message = "Bad request. Please refer to the API specification for proper request.";
        this.logId = logId;
    }
}

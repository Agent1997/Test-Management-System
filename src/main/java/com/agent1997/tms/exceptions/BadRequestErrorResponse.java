package com.agent1997.tms.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class BadRequestErrorResponse{
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final Integer statusCode = HttpStatus.BAD_REQUEST.value();
    private final String reason = HttpStatus.BAD_REQUEST.getReasonPhrase();
    private final String message ="Bad request. Please refer to the API specification for proper request.";
    private final String logId;

    public BadRequestErrorResponse(String logId) {
        this.logId = logId;

    }
}

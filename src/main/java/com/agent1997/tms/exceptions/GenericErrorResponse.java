package com.agent1997.tms.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
public class GenericErrorResponse {
    Integer statusCode;
    String reason;
    String logId;

    @Setter
    String message;

    public GenericErrorResponse(HttpStatus httpStatus, String message, String logId){
        this.statusCode = httpStatus.value();
        this.reason = httpStatus.getReasonPhrase();
        this.message = message;
        this.logId = logId;
    }
}

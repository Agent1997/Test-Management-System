package com.agent1997.tms.exceptions;

public class DatabaseException extends RuntimeException{

    public DatabaseException(String message, Throwable cause){
        super(message, cause);
    }
}

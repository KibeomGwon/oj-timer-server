package com.oj_timer.server.exception_handler;

import org.springframework.http.HttpStatus;

public abstract class BaseCustomException extends RuntimeException {
    public BaseCustomException(String message) {
        super(message);
    }

    abstract public HttpStatus getStatus();
}

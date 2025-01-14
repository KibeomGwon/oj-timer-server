package com.oj_timer.server.exception_handler.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ForbiddenException extends RuntimeException {
    private HttpStatus status;

    public ForbiddenException(String message) {
        super(message);
        status = HttpStatus.FORBIDDEN;
    }
}

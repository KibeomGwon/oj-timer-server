package com.oj_timer.server.exception_handler.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ForbiddenException extends RuntimeException {
    private HttpStatus status;
    private int code;

    public ForbiddenException(String message) {
        super(message);
        status = HttpStatus.FORBIDDEN;
        code = HttpStatus.FORBIDDEN.value();
    }
}

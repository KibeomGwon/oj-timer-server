package com.oj_timer.server.exception_handler.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotFoundException extends RuntimeException {
    private HttpStatus status;
    private int code;

    public NotFoundException(String message) {
        super(message);
        status = HttpStatus.NOT_FOUND;
        code = HttpStatus.NOT_FOUND.value();
    }
}

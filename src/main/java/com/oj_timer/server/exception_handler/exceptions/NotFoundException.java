package com.oj_timer.server.exception_handler.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotFoundException extends RuntimeException {
    private HttpStatus status;

    public NotFoundException(String message) {
        super(message);
        status = HttpStatus.NOT_FOUND;
    }
}

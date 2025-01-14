package com.oj_timer.server.exception_handler.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BadRequestException extends RuntimeException {
    private HttpStatus status;

    public BadRequestException(String message) {
        super(message);
        status = HttpStatus.BAD_REQUEST;
    }
}

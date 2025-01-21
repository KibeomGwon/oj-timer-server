package com.oj_timer.server.exception_handler.exceptions;

import com.oj_timer.server.exception_handler.BaseCustomException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BadRequestException extends BaseCustomException {
    private HttpStatus status;

    public BadRequestException(String message) {
        super(message);
        status = HttpStatus.BAD_REQUEST;
    }

    public HttpStatus getStatus() {
        return status;
    }
}

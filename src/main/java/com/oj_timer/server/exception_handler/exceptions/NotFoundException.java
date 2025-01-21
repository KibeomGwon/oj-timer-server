package com.oj_timer.server.exception_handler.exceptions;

import com.oj_timer.server.exception_handler.BaseCustomException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotFoundException extends BaseCustomException {
    private HttpStatus status;

    public NotFoundException(String message) {
        super(message);
        status = HttpStatus.NOT_FOUND;
    }

    public HttpStatus getStatus() {
        return status;
    }
}

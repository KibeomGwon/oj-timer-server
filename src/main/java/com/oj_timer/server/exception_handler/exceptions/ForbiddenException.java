package com.oj_timer.server.exception_handler.exceptions;

import com.oj_timer.server.exception_handler.BaseCustomException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ForbiddenException extends BaseCustomException {
    private HttpStatus status;

    public ForbiddenException(String message) {
        super(message);
        status = HttpStatus.FORBIDDEN;
    }

    public HttpStatus getStatus() {
        return status;
    }
}

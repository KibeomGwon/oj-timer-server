package com.oj_timer.server.exception_handler;

import com.oj_timer.server.controller.api.http_util.ErrorResponse;
import com.oj_timer.server.exception_handler.exceptions.BadRequestException;
import com.oj_timer.server.exception_handler.exceptions.ForbiddenException;
import com.oj_timer.server.exception_handler.exceptions.NotFoundException;
import com.oj_timer.server.exception_handler.exceptions.UnauthorizedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = {"com.oj_timer.server.controller.api"})
public class RestExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ErrorResponse illegalAccessException(BadRequestException e) {
        return new ErrorResponse(e.getStatus(), e.getMessage(), e.getCode());
    }

    @ExceptionHandler(NotFoundException.class)
    public ErrorResponse notFoundException(NotFoundException e) {
        return new ErrorResponse(e.getStatus(), e.getMessage(), e.getCode());
    }

    @ExceptionHandler(ForbiddenException.class)
    public ErrorResponse forbiddenException(ForbiddenException e) {
        return new ErrorResponse(e.getStatus(), e.getMessage(), e.getCode());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ErrorResponse unauthorizedException(UnauthorizedException e) {
        return new ErrorResponse(e.getStatus(), e.getMessage(), e.getCode());
    }
}

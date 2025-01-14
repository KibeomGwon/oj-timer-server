package com.oj_timer.server.exception_handler;

import com.oj_timer.server.exception_handler.exceptions.BadRequestException;
import com.oj_timer.server.exception_handler.exceptions.ForbiddenException;
import com.oj_timer.server.exception_handler.exceptions.NotFoundException;
import com.oj_timer.server.exception_handler.exceptions.UnauthorizedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = {"com.oj_timer.server.controller.api"})
public class RestExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> illegalAccessException(BadRequestException e) {
        return new ResponseEntity<String>(e.getMessage(), e.getStatus());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> notFoundException(NotFoundException e) {
        return new ResponseEntity<String>(e.getMessage(), e.getStatus());
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<String> forbiddenException(ForbiddenException e) {
        return new ResponseEntity<String>(e.getMessage(), e.getStatus());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<String> unauthorizedException(UnauthorizedException e) {
        return new ResponseEntity<String>(e.getMessage(), e.getStatus());
    }
}

package com.oj_timer.server.controller.api.http_util;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Data
public class Response<T> {
    private HttpStatus status;
    private T message;
    private int code;
}

package com.oj_timer.server.controller.api.http_enum;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class CustomHttpStatusTest {

    @Test
    public void test() throws Exception {
        // given
        Assertions.assertThat(CustomHttpStatus.NOT_FOUNT.getCode()).isEqualTo(404);
        // when
        System.out.println(CustomHttpStatus.NOT_FOUNT.getCode());
        // then
    }
}
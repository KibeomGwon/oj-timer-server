package com.oj_timer.server.controller.api;

import com.oj_timer.server.controller.api.auth_jwt.JwtDto;
import com.oj_timer.server.controller.api.auth_jwt.JwtGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class JwtControllerTest {

    @Autowired
    JwtGenerator generator;
    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("Jwt 생성")
    void createJwt() throws Exception {
        // given
        JwtDto jwtDto = generator.generate("xtcc02332@naver.com");

        MvcResult authorization = mockMvc.perform(
                get("api/authorization").header("Authorization", jwtDto.getAccessToken())
        ).andReturn();
        // when
        // then
    }
}

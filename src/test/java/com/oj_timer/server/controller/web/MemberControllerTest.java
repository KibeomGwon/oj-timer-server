package com.oj_timer.server.controller.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oj_timer.server.controller.api.auth_jwt.JwtDto;
import com.oj_timer.server.controller.web.session.SessionConst;
import com.oj_timer.server.repository.RefreshTokenRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    RefreshTokenRepository repository;

//    @Test
    public void loginTest() throws Exception {
        // given
        String email = "user@email.com";
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(SessionConst.LOGIN_MANAGER, email);

        // when
        MvcResult mvcResult = mockMvc
                .perform(get("/api/authorization/jwts").session(session))
                .andReturn();
        String tokens = mvcResult.getResponse()
                .getContentAsString();

        JwtDto dto = (new ObjectMapper()).readValue(tokens, JwtDto.class);
        String findRefresh = repository.findOne(email);

        // then
        assertThat(findRefresh).isEqualTo(dto.getRefreshToken());
    }
}
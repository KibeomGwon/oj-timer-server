package com.oj_timer.server.controller.web.form;

import com.oj_timer.server.dto.MemberDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class LoginForm {

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String password;
}

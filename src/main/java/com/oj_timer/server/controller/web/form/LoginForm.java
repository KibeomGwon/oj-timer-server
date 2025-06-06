package com.oj_timer.server.controller.web.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginForm {

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String password;
}

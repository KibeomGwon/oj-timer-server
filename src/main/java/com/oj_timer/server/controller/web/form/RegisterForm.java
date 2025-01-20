package com.oj_timer.server.controller.web.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RegisterForm {

    @NotEmpty(message = "이메일을 입력해주세요")
    @Email
    private String email;

    @NotEmpty(message = "비밀번호를 입력해주세요")
    private String password;

    @NotEmpty(message = "휴대폰 번호를 입력해주세요")
    @Pattern(regexp = "^\\d{11}$", message = "11 개의 숫자만 입력해주세요")
    private String phone;
}

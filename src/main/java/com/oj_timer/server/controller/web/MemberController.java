package com.oj_timer.server.controller.web;

import com.oj_timer.server.controller.web.form.LoginForm;
import com.oj_timer.server.controller.web.form.RegisterForm;
import com.oj_timer.server.controller.web.session.SessionConst;
import com.oj_timer.server.dto.domain.MemberDto;
import com.oj_timer.server.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute LoginForm loginForm) {
        return "login/login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginForm loginForm,
                        BindingResult bindingResult,
                        @RequestParam(defaultValue = "/") String redirectURL,
                        HttpServletRequest request)
    {
        if (bindingResult.hasErrors()) {
            return "login/login";
        }

        MemberDto loginMember = memberService.login(toMemberDto(loginForm).toMember());

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MANAGER, loginMember.getEmail());

        return "redirect:" + redirectURL;
    }

    @GetMapping("/register")
    public String registerForm(@ModelAttribute RegisterForm resigterForm) {
        return "login/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute RegisterForm registerForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "login/register";
        }
        memberService.save(toMemberDto(registerForm).toMember());
        return "redirect:/login";
    }


    private MemberDto toMemberDto(LoginForm form) {
        return MemberDto.builder()
                .email(form.getEmail())
                .password(form.getPassword())
                .build();
    }

    private MemberDto toMemberDto(RegisterForm form) {
        return MemberDto.builder()
                .email(form.getEmail())
                .password(form.getPassword())
                .phone(form.getPhone())
                .build();
    }
}

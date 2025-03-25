package com.metacoding.bankv1.user;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final HttpSession session;

    @GetMapping("/login-form")
    public String loginForm() {
        session.setAttribute("metacoding", "apple");
        return "user/login-form";
    }

    @GetMapping("/join-form")
    public String joinForm() {
        String value = (String) session.getAttribute("metacoding");
        System.out.println(value);
        return "user/join-form";
    }

    @GetMapping("/account/list")
    public String accountList() {
        return "account/list";
    }

    @PostMapping("/join")
    public String join(UserRequest.JoinDTO joinDTO) {
        userService.회원가입(joinDTO);
        return "redirect:/login-form";
    }
}

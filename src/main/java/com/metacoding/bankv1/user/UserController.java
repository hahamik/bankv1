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


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 서랍장에 있는거 버림
//        session.removeAttribute("user"); 골라서 버림
        return "redirect:/";
    }

    @PostMapping("/login")// 원래 select는 get인데 로그인은 post요청이다. 왜? -> 예외임 왜냐하면 조회시에 주소에 걸리기 때문에 보안 때문에 password를 바디에 담아야 됨
    public String login(UserRequest.LoginDTO loginDTO) {
        User sessionUser = userService.로그인(loginDTO);
        session.setAttribute("sessionUser", sessionUser);//stateful
        return "redirect:/";
    }

    @GetMapping("/login-form")
    public String loginForm() {

        return "user/login-form";
    }

    @GetMapping("/join-form")
    public String joinForm() {
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

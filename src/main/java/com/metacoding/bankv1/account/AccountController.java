package com.metacoding.bankv1.account;

import com.metacoding.bankv1.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class AccountController {

    private final AccountService accountService;
    private final HttpSession session;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/account/save-form")
    public String saveForm() {
        User sessionUser = (User) session.getAttribute("sessionUser");

        if (sessionUser == null) {
            throw new RuntimeException("로그인이 필요합니다.");
        }

        return "account/save-form";// null 체크 후 호출
    }


    @PostMapping("/account/save")
    public String save(AccountRequest.SaveDTO saveDTO, HttpSession session) {
        // 반복되는 부가 로직( 계속 적어야하는 코드) => 나중가면 리플렉션으로 해결함.(어노테이션 만들어서)
        // 인증체크 코드
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            throw new RuntimeException("로그인해라");
        }
        // /인증체크 코드
        accountService.계좌생성(saveDTO, sessionUser.getId());
        return "redirect:/account";
    }

    @GetMapping("/account")
    public String list(HttpServletRequest request) {
        // 인증 체크
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            throw new RuntimeException("로그인이 필요합니다.");
        }
        // 세션 id로 계좌 목록 조회
        List<Account> accountList = accountService.계좌목록(sessionUser.getId());

        // view로 넘기기
        request.setAttribute("models", accountList);
        return "account/list";
    }

    @GetMapping("/account/transfer-form")
    public String transferForm() {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            throw new RuntimeException("로그인이 필요합니다.");
        }
        return "account/transfer-form";
    }

    @PostMapping("/account/transfer")
    public String transfer(AccountRequest.TransferDTO transferDTO, HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            throw new RuntimeException("로그인이 필요합니다.");
        }
        accountService.계좌이체(transferDTO, sessionUser.getId());
        return "redirect:/";//TODO
    }
}

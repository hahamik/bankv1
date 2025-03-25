package com.metacoding.bankv1.account;

import com.metacoding.bankv1.user.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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

        System.out.println(sessionUser.getId()); // null 체크 후 호출
        return "account/save-form";
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
        return "redirect:/";
    }

//    @PostMapping("/account/list")
//    public String list(HttpServletRequest request) {
//        // 인증 체크
//        User sessionUser = (User) session.getAttribute("sessionUser");
//        if (sessionUser == null) {
//            throw new RuntimeException("로그인이 필요합니다.");
//        }
//        // 세션 id로 계좌 목록 조회
//        List<Account> accountList = accountService.계좌목록(sessionUser.getId());
//        request.setAttribute("accounts", accountList);
//        return "account/list";
//    }
}

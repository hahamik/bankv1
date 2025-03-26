package com.metacoding.bankv1.account;


import com.metacoding.bankv1.account.history.HistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final HistoryRepository historyRepository;

    @Transactional
    public void 계좌생성(AccountRequest.SaveDTO saveDTO, int userId) {
        accountRepository.save(saveDTO.getNumber(), saveDTO.getPassword(), saveDTO.getBalance(), userId);
    }

    @Transactional
    public List<Account> 계좌목록(Integer userId) {
        return accountRepository.findAllByUserId(userId);
    }

    @Transactional
    public void 계좌이체(AccountRequest.TransferDTO transferDTO, Integer userId) {
        // {부가로직}
        // 1. 출금 계좌 조회
        Account withdrawAccount = accountRepository.findByNumber(transferDTO.getWithdrawNumber());
        // 2. 출금 계좌 없으면 RuntimeException
        if (withdrawAccount == null) throw new RuntimeException("출금계좌가 존재하지 않습니다.");
        // 3. 입금 계좌 조회
        Account depositAccount = accountRepository.findByNumber(transferDTO.getDepositNumber());
        // 4. 입금 계좌 없으면 RuntimeException
        if (depositAccount == null) throw new RuntimeException("입금계좌가 존재하지 않습니다.");
        // 5. 출금 계좌의 잔액 조회  amount와 잔액 비교(출금계좌와) amount가 더 크면 RuntimeException
        if (withdrawAccount.getBalance() < transferDTO.getAmount()) {
            throw new RuntimeException("출금 계좌의 잔액 : " + withdrawAccount.getBalance() + ", 이체하려는 금액 : " + transferDTO.getAmount());
        }
        // 6. 출금 비밀번호 확인(동일한지 체크 withdrawNumber의 password와 password)
        if (!(withdrawAccount.getPassword().equals(transferDTO.getWithdrawPassword())))
            throw new RuntimeException("출금 계좌 비밀번호가 틀렸습니다.");

        // {부가로직}

        // 7. 출금계좌와 로그인한 유저가 동일한 인물인지 권한 체크
        if (!(withdrawAccount.getUserId().equals(userId))) throw new RuntimeException("출금계좌의 권한이 없습니다.");

        // 8. account update 출금계좌 잔액 변경
        int withdrawBalance = withdrawAccount.getBalance();
        withdrawBalance -= transferDTO.getAmount();
        accountRepository.updateNumber(withdrawAccount.getPassword(), withdrawBalance, withdrawAccount.getNumber());
        // 9. account update 출금계좌 잔액 변경
        int depositBalance = depositAccount.getBalance();
        depositBalance += transferDTO.getAmount();
        accountRepository.updateNumber(depositAccount.getPassword(), depositBalance, depositAccount.getNumber());

        // 10. history save  여기선 위에서 다 검증해서 dto에 있는 데이터 써도 됨
        historyRepository.save(transferDTO.getWithdrawNumber(), transferDTO.getDepositNumber(), transferDTO.getAmount(), withdrawBalance);

    }
}

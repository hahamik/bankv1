package com.metacoding.bankv1.account;

import com.metacoding.bankv1.account.history.HistoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import(AccountRepository.class)
@DataJpaTest
public class AccountRepositoryTest {
    @Autowired
    private HistoryRepository historyRepository;

    @Test
    public void findAllByNumber_test() {
        // given when
        int number = 1111;

        // when
//        List<AccountResponse.DetailDTO> detailList = historyRepository.findAllByNumber(number,type);
//
//        // eye
//        for (AccountResponse.DetailDTO detail : detailList) {
//            System.out.println(detail);
//        }
    }
}
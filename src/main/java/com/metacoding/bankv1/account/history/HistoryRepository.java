package com.metacoding.bankv1.account.history;

import com.metacoding.bankv1.account.AccountResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class HistoryRepository {

    private final EntityManager em;

    public void save(Integer withdrawNumber, Integer depositNumber, Integer amount, Integer withdrawBalance, Integer depositBalance) {
        Query query = em.createNativeQuery("insert into history_tb(withdraw_number, deposit_number, amount, withdraw_balance,deposit_balance, created_at) values (?, ?, ?, ?, ?, now())");
        query.setParameter(1, withdrawNumber);
        query.setParameter(2, depositNumber);
        query.setParameter(3, amount);
        query.setParameter(4, withdrawBalance);
        query.setParameter(5, depositBalance);
        query.executeUpdate();
    }

    public List<AccountResponse.DetailDTO> findAllByNumber(int number, String type) {
        String allSql = """
                select 
                dt.account_number,
                dt.account_balance,
                dt.account_owner,
                substr(created_at, 1, 16) created_at,
                withdraw_number w_number,
                deposit_number d_number,
                amount amount,
                case when withdraw_number = ? then withdraw_balance 
                else deposit_balance 
                end balance,
                case when withdraw_number = ? then '출금' 
                else '입금' 
                end type 
                from history_tb ht 
                inner join (select at.number account_number, at.balance account_balance, ut.fullname account_owner 
                from account_tb at 
                inner join user_tb ut on at.user_id = ut.id 
                where at.number = ?) dt on 1=1 
                where deposit_number = ? or withdraw_number = ?;
                """;

        String withdrawSql = """
                select 
                dt.account_number,
                dt.account_balance,
                dt.account_owner,
                substr(created_at, 1, 16) created_at,
                withdraw_number w_number,
                deposit_number d_number,
                amount amount,
                withdraw_balance balance,
                '출금' type
                from history_tb ht 
                inner join (select at.number account_number, at.balance account_balance, ut.fullname account_owner 
                from account_tb at 
                inner join user_tb ut on at.user_id = ut.id 
                where at.number = ?) dt on 1=1 
                where withdraw_number = ?;
                """;

        String depositSql = """
                select 
                dt.account_number,
                dt.account_balance,
                dt.account_owner,
                substr(created_at, 1, 16) created_at,
                withdraw_number w_number,
                deposit_number d_number,
                amount amount,
                deposit_balance balance,
                '입금' type 
                from history_tb ht 
                inner join (select at.number account_number, at.balance account_balance, ut.fullname account_owner 
                from account_tb at 
                inner join user_tb ut on at.user_id = ut.id 
                where at.number = ?) dt on 1=1 
                where deposit_number = ?;
                """;

        Query query = null;
        if (type.equals("입금")) {
            query = em.createNativeQuery(depositSql);
            query.setParameter(1, number);
            query.setParameter(2, number);
        } else if (type.equals("출금")) {
            query = em.createNativeQuery(withdrawSql);
            query.setParameter(1, number);
            query.setParameter(2, number);
        } else {
            query = em.createNativeQuery(allSql);
            query.setParameter(1, number);
            query.setParameter(2, number);
            query.setParameter(3, number);
            query.setParameter(4, number);
            query.setParameter(5, number);
        }

        List<Object[]> obsList = query.getResultList();
        List<AccountResponse.DetailDTO> detailList = new ArrayList<>();

        for (Object[] obs : obsList) {
            AccountResponse.DetailDTO detail =
                    new AccountResponse.DetailDTO(
                            (String) obs[0],
                            (int) obs[1],
                            (int) obs[2],
                            (String) obs[3],
                            (int) obs[4],
                            (int) obs[5],
                            (int) obs[6],
                            (int) obs[7],
                            (String) obs[8]
                    );
            detailList.add(detail);
        }
        return detailList;
    }
}

package com.metacoding.bankv1.account;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class AccountRepository {

    private final EntityManager em;


    public void save(Integer number, String password, Integer balance, int userId) {
        Query query = em.createNativeQuery("INSERT INTO account_tb(number, password,balance,user_id,created_at)VALUES(?,?,?,?,now())");
        query.setParameter(1, number);
        query.setParameter(2, password);
        query.setParameter(3, balance);
        query.setParameter(4, userId);
        query.executeUpdate();
    }


//    public List<Account> findAccountByUserId(Integer userId) {
//        Query query = em.createNativeQuery("SELECT * FROM account_tb WHERE user_id = ?", Account.class);
//        query.setParameter(1, userId);
//        return query.getResultList();
//    }
}

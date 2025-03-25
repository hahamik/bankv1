package com.metacoding.bankv1.user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor // 파이널이 붙은 애들로 생성자를 만들어줌
@Repository
public class UserRepository {

    private final EntityManager em; // 파이널이 붙으면 생성할 때마다 초기화를 해줘야 한다.

    public User findByUsername(String username) {
        Query query = em.createNativeQuery("SELECT * FROM user_tb WHERE username = ?", User.class);
        query.setParameter(1, username);
        try {
            return (User) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public void save(String username, String password, String fullname) {
        Query query = em.createNativeQuery("INSERT INTO user_tb(username, password,fullname,created_at)VALUES(?,?,?,now())");
        query.setParameter(1, username);
        query.setParameter(2, password);
        query.setParameter(3, fullname);
        query.executeUpdate();
    }

}

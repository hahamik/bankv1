package com.metacoding.bankv1.account;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Table(name = "account_tb")
@Entity
public class Account {

    @Id
    private Integer number; // 계좌번호 PK
    private String password;
    private Integer balance; // 잔액
    private Integer userId; // FK
    private Timestamp createdAt;

    public void 출금(int amount) {
        this.balance = this.balance - amount;
    }

    public void 입금(int amount) {
        this.balance = this.balance + amount;
    }

    public void 계좌주인검사(int sessionUserId) {
        if (!(userId.equals(sessionUserId))) {
            throw new RuntimeException("출금계좌의 권한이 없습니다");
        }
    }

    public void 잔액검사(int amount) {
        if (balance < amount) {
            throw new RuntimeException("출금 계좌의 잔액 : " + balance + ", 이체하려는 금액 : " + amount);
        }
    }

    public void 비밀번호검사(String withdrawPassword) {
        if (!(password.equals(withdrawPassword))) {
            throw new RuntimeException("출금 계좌 비밀번호가 틀렸습니다");
        }
    }
}

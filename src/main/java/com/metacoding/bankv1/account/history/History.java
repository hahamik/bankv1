package com.metacoding.bankv1.account.history;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Table(name = "history_tb")
@Entity
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer withdrawNumber;// FK/1111
    private Integer depositNumber;// FK/2222
    private Integer amount;
    private Integer withdrawBalance;// 그 시점의 잔액
    private Integer depositBalance;
    private Timestamp createdAt;

}

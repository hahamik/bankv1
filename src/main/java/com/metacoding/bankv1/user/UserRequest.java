package com.metacoding.bankv1.user;

import lombok.Data;

import java.sql.Timestamp;

public class UserRequest {

    @Data
    public static class JoinDTO {
        private String username;
        private String password;
        private String fullname;
        private Timestamp createdAt;
    }

    @Data
    public static class LoginDTO {
        private String username;
        private String password;
    }

}

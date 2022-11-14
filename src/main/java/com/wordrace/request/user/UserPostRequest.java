package com.wordrace.request.user;

import lombok.Data;

@Data
public class UserPostRequest {
    private String email;
    private String password;
}

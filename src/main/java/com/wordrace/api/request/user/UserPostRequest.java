package com.wordrace.api.request.user;

import lombok.Data;

@Data
public class UserPostRequest {
    private String email;
    private String password;
}

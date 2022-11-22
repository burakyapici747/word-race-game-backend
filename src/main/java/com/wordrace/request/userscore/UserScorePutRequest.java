package com.wordrace.request.userscore;

import lombok.Data;

@Data
public class UserScorePutRequest {
    private String userId;
    private String gameId;
    private int score;
}

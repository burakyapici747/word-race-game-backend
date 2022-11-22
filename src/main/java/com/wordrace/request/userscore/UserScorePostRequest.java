package com.wordrace.request.userscore;

import lombok.Data;

@Data
public class UserScorePostRequest {
    private String userId;
    private String gameId;
    private int score;
}

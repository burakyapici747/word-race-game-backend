package com.wordrace.api.request.userscore;

import lombok.Data;

@Data
public class UserScorePostRequest {
    private String userId;
    private String gameId;
    private int score;
}

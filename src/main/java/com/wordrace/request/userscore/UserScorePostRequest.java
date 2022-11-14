package com.wordrace.request.userscore;

import lombok.Data;

@Data
public class UserScorePostRequest {
    private Long userId;
    private Long gameId;
    private int score;
}

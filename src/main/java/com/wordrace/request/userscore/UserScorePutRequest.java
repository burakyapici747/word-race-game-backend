package com.wordrace.request.userscore;

import lombok.Data;

@Data
public class UserScorePutRequest {
    private Long userId;
    private Long gameId;
    private int score;
}

package com.wordrace.request.user;

import lombok.Data;

@Data
public class UserPostScoreRequest {
    private Long userId;
    private Long gameId;
    private int score;
}

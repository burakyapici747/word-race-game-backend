package com.wordrace.request.user;

import lombok.Data;

@Data
public class UserPostScoreRequest {
    private String userId;
    private String gameId;
    private int score;
}

package com.wordrace.dto;

import lombok.Data;

@Data
public class GameUserScoreDto {
    private String id;
    private UserDto user;
    private int score;
}

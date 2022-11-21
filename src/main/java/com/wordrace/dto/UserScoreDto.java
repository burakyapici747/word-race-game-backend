package com.wordrace.dto;

import lombok.Data;

@Data
public class UserScoreDto {
    private Long id;
    private GameDto game;
    private UserDto user;
    private int score;
}

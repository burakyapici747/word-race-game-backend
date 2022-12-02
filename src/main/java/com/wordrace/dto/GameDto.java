package com.wordrace.dto;

import lombok.Data;
import java.util.List;

@Data
public class GameDto {
    private String id;
    private GameRoomDto room;
    private List<GameUserScoreDto> userScores;
    private List<WordDto> words;
    private int totalScore;
}

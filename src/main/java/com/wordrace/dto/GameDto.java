package com.wordrace.dto;

import com.wordrace.model.Room;
import com.wordrace.model.UserScore;
import com.wordrace.model.Word;
import lombok.Data;

import java.util.List;

@Data
public class GameDto {

    private Long id;
    private RoomDto room;
    private List<UserScoreDto> userScores;
    private List<WordDto> words;
    private int totalScore;
}

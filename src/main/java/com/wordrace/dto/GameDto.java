package com.wordrace.dto;

import com.wordrace.model.Room;
import com.wordrace.model.UserScore;
import com.wordrace.model.Word;

import java.util.List;

public class GameDto {

    private Long id;
    private Room room;
    private List<UserScore> userScores;
    private List<Word> words;
    private int totalScore;
}

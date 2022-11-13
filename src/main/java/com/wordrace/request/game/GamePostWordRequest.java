package com.wordrace.request.game;

import com.wordrace.model.Word;
import lombok.Data;

import java.util.List;

@Data
public class GamePostWordRequest {
    private List<Word> words;
}

package com.wordrace.request.game;

import com.wordrace.request.word.WordPostRequest;
import lombok.Data;

import java.util.List;

@Data
public class GamePostWordRequest {
    private List<WordPostRequest> words;
}

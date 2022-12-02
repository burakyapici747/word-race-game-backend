package com.wordrace.api.request.word;

import lombok.Data;

import java.util.List;

@Data
public class WordPostGameRequest {
    private List<String> wordIds;
}
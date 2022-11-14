package com.wordrace.request.word;

import com.wordrace.model.Language;
import lombok.Data;

@Data
public class WordPostRequest {
    private String text;
    private Language language;
}

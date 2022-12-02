package com.wordrace.api.request.word;

import com.wordrace.model.Language;
import lombok.Data;

@Data
public class WordPutRequest {
    private String text;
    private Language language;
}

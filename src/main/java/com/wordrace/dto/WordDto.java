package com.wordrace.dto;

import com.wordrace.model.Language;
import lombok.Data;

@Data
public class WordDto {
    private String id;
    private String text;
    private Language language;
}

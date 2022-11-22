package com.wordrace.service;

import com.wordrace.dto.WordDto;
import com.wordrace.model.Word;
import com.wordrace.request.word.WordPostRequest;
import com.wordrace.request.word.WordPutRequest;
import com.wordrace.result.DataResult;
import com.wordrace.result.Result;

import java.util.List;
import java.util.UUID;

public interface WordService {
    DataResult<List<WordDto>> getAllWords();
    DataResult<WordDto> getWordById(UUID id);
    DataResult<WordDto> createWord(WordPostRequest wordPostRequest);
    DataResult<WordDto> updateWordById(UUID id, WordPutRequest wordPutRequest);
    Result deleteWordById(UUID id);
}
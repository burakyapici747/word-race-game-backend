package com.wordrace.service;

import com.wordrace.dto.WordDto;
import com.wordrace.model.Word;
import com.wordrace.request.word.WordPostRequest;
import com.wordrace.request.word.WordPutRequest;
import com.wordrace.result.DataResult;
import com.wordrace.result.Result;

import java.util.List;

public interface WordService {

    //GET OPERATIONS
    DataResult<List<WordDto>> getAllWords();
    DataResult<WordDto> getWordById(Long id);

    //POST OPERATIONS
    DataResult<WordDto> createWord(WordPostRequest wordPostRequest);

    //PUT OPERATIONS
    DataResult<WordDto> updateWordById(Long id, WordPutRequest wordPutRequest);

    //DELETE OPERATIONS
    Result deleteWordById(Long id);

}
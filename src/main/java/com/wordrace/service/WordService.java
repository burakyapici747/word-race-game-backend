package com.wordrace.service;

import com.wordrace.model.Word;
import com.wordrace.request.word.WordPostRequest;
import com.wordrace.request.word.WordPutRequest;
import com.wordrace.result.DataResult;
import com.wordrace.result.Result;

import java.util.List;

public interface WordService {

    //GET OPERATIONS
    DataResult<List<Word>> getAllWords();
    DataResult<Word> getWordById(Long id);

    //POST OPERATIONS
    DataResult<Word> createWord(WordPostRequest wordPostRequest);

    //PUT OPERATIONS
    DataResult<Word> updateWordById(Long id, WordPutRequest wordPutRequest);

    //DELETE OPERATIONS
    Result deleteWordById(Long id);

}
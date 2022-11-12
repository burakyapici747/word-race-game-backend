package com.wordrace.service;

import com.wordrace.model.Word;
import com.wordrace.result.DataResult;
import java.util.List;

public interface WordService {

    //GET OPERATIONS
    DataResult<List<Word>> getAllWords();
    DataResult<Word> getWordById(Long id);

    //POST OPERATIONS
    DataResult<Word> createWord(Word word);

    //PUT OPERATIONS
    DataResult<Word> updateWord(Long id, Word word);

    //DELETE OPERATIONS
    DataResult<Boolean> deleteWordById(Long id);

}
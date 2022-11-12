package com.wordrace.service;

import com.wordrace.model.Word;

import java.util.List;
import java.util.Optional;

public interface WordService {

    //GET OPERATIONS
    Optional<List<Word>> getAllWords();
    Optional<Word> getWordById(Long id);

    //POST OPERATIONS
    Optional<Word> createWord(Word word);

    //PUT OPERATIONS
    Optional<Word> updateWord(Long id, Word word);

    //DELETE OPERATIONS
    boolean deleteWordById(Long id);


}

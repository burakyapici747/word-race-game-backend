package com.wordrace.service.impl;

import com.wordrace.constant.ResultMessages;
import com.wordrace.model.Word;
import com.wordrace.repository.WordRepository;
import com.wordrace.result.*;
import com.wordrace.service.WordService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WordServiceImpl implements WordService {

    private final WordRepository wordRepository;

    public WordServiceImpl(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    @Override
    public DataResult<List<Word>> getAllWords() {
        final List<Word> words = new ArrayList<>(wordRepository.findAll());
        return new SuccessDataResult<>(words, ResultMessages.EMPTY);
    }

    @Override
    public DataResult<Word> getWordById(Long id) {
        final Word word = findById(id);
        return new SuccessDataResult<>(word, ResultMessages.EMPTY);
    }

    @Override
    public DataResult<Word> createWord(Word word) {
        final Word createdWord = wordRepository.save(word);
        return new SuccessDataResult<>(createdWord, ResultMessages.SUCCESS_CREATE);
    }

    @Override
    public DataResult<Word> updateWordById(Long id, Word word) {
        final Word wordToUpdate = findById(id);
        final Optional<Word> optionalWordToUpdateByText = wordRepository.findByText(word.getText());
        boolean isWordAlreadyExist = optionalWordToUpdateByText.isPresent()
                && !id.equals(optionalWordToUpdateByText.get().getId());

        if(isWordAlreadyExist){
            return new SuccessDataResult<>(null, ResultMessages.ALREADY_EXIST);
        }

        wordRepository.save(wordToUpdate);
        return new SuccessDataResult<>(optionalWordToUpdateByText.get(), ResultMessages.SUCCESS_UPDATE);
    }

    @Override
    public Result deleteWordById(Long id) {
        final Word word = findById(id);
        wordRepository.delete(word);
        return new SuccessResult(ResultMessages.SUCCESS_DELETE);
    }

    private Word findById(Long id){
        Optional<Word> wordOptional = wordRepository.findById(id);
        return wordOptional.get();
    }
}

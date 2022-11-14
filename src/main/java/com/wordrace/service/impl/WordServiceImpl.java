package com.wordrace.service.impl;

import com.wordrace.constant.ResultMessages;
import com.wordrace.exception.EntityAlreadyExistException;
import com.wordrace.exception.EntityNotFoundException;
import com.wordrace.model.Word;
import com.wordrace.repository.WordRepository;
import com.wordrace.request.word.WordPostRequest;
import com.wordrace.request.word.WordPutRequest;
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
    public DataResult<Word> createWord(WordPostRequest wordPostRequest) {
        Word word = new Word();
        word.setText(wordPostRequest.getText());
        word.setLanguage(wordPostRequest.getLanguage());
        return new SuccessDataResult<>(word, ResultMessages.SUCCESS_CREATE);
    }

    @Override
    public DataResult<Word> updateWordById(Long id, WordPutRequest wordPutRequest) {
        final Word wordToUpdate = findById(id);
        final Optional<Word> optionalWordToUpdateByText = wordRepository.findByText(wordPutRequest.getText());
        boolean isWordAlreadyExist = optionalWordToUpdateByText.isPresent()
                && !id.equals(optionalWordToUpdateByText.get().getId());

        if(isWordAlreadyExist){
            throw new EntityAlreadyExistException(ResultMessages.ALREADY_EXIST);
        }

        return new SuccessDataResult<>(wordRepository.save(wordToUpdate), ResultMessages.SUCCESS_UPDATE);
    }

    @Override
    public Result deleteWordById(Long id) {
        final Word word = findById(id);
        wordRepository.delete(word);
        return new SuccessResult(ResultMessages.SUCCESS_DELETE);
    }

    private Word findById(Long id){
        return wordRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(ResultMessages.NOT_FOUND_DATA));
    }
}

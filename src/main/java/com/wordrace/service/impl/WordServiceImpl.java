package com.wordrace.service.impl;

import com.wordrace.constant.ResultMessages;
import com.wordrace.dto.*;
import com.wordrace.exception.EntityAlreadyExistException;
import com.wordrace.exception.EntityNotFoundException;
import com.wordrace.model.Word;
import com.wordrace.repository.WordRepository;
import com.wordrace.request.word.WordPostRequest;
import com.wordrace.request.word.WordPutRequest;
import com.wordrace.result.*;
import com.wordrace.service.WordService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class WordServiceImpl implements WordService {
    private final WordRepository wordRepository;
    private final ModelMapper modelMapper;

    public WordServiceImpl(WordRepository wordRepository, ModelMapper modelMapper) {
        this.wordRepository = wordRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public DataResult<List<WordDto>> getAllWords() {
        final List<WordDto> wordDtos = wordRepository.findAll()
                .stream().map(word -> modelMapper.map(word, WordDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(wordDtos, ResultMessages.EMPTY);
    }

    @Override
    public DataResult<WordDto> getWordById(UUID id) {
        final Word word = findById(id);

        return new SuccessDataResult<>(modelMapper.map(word, WordDto.class), ResultMessages.EMPTY);
    }

    @Override
    public DataResult<WordDto> createWord(WordPostRequest wordPostRequest) {
        boolean isAnySameWord = wordRepository.findByTextAndLanguage(wordPostRequest.getText(), wordPostRequest.getLanguage())
                .isPresent();

        if(isAnySameWord)
            throw new EntityAlreadyExistException(ResultMessages.ALREADY_EXIST);

        final Word word = new Word();

        word.setText(wordPostRequest.getText());
        word.setLanguage(wordPostRequest.getLanguage());

        return new SuccessDataResult<>(modelMapper.map(wordRepository.save(word), WordDto.class), ResultMessages.SUCCESS_CREATE);
    }

    @Override
    public DataResult<WordDto> updateWordById(UUID id, WordPutRequest wordPutRequest) {
        final Word wordToUpdate = findById(id);

        boolean isAnySameWord = wordRepository
                .findByTextAndLanguage(wordPutRequest.getText(), wordPutRequest.getLanguage())
                .isPresent();

        if(isAnySameWord){
            throw new EntityAlreadyExistException(ResultMessages.ALREADY_EXIST);
        }

        wordToUpdate.setText(wordPutRequest.getText());
        wordToUpdate.setLanguage(wordPutRequest.getLanguage());

        return new SuccessDataResult<>(modelMapper.map(wordRepository.save(wordToUpdate), WordDto.class), ResultMessages.SUCCESS_UPDATE);
    }

    @Override
    public Result deleteWordById(UUID id) {
        final Word word = findById(id);

        wordRepository.delete(word);

        return new SuccessResult(ResultMessages.SUCCESS_DELETE);
    }

    private Word findById(UUID id){
        return wordRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(ResultMessages.NOT_FOUND_DATA));
    }
}

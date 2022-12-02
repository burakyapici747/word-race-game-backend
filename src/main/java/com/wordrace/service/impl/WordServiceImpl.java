package com.wordrace.service.impl;

import com.wordrace.api.response.BaseResponse;
import com.wordrace.api.response.DataResponse;
import com.wordrace.api.response.SuccessDataResponse;
import com.wordrace.api.response.SuccessResponse;
import com.wordrace.constant.ResultMessages;
import com.wordrace.dto.*;
import com.wordrace.exception.EntityNotFoundException;
import com.wordrace.model.Game;
import com.wordrace.model.Word;
import com.wordrace.repository.WordRepository;
import com.wordrace.api.request.word.WordPostGameRequest;
import com.wordrace.api.request.word.WordPostRequest;
import com.wordrace.api.request.word.WordPutRequest;
import com.wordrace.response.*;
import com.wordrace.service.WordService;
import com.wordrace.util.GlobalHelper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WordServiceImpl implements WordService {

    private final WordRepository wordRepository;
    private final ModelMapper modelMapper;
    private final GameServiceImpl gameService;

    public WordServiceImpl(final WordRepository wordRepository, final ModelMapper modelMapper, GameServiceImpl gameService) {
        this.wordRepository = wordRepository;
        this.modelMapper = modelMapper;
        this.gameService = gameService;
    }

    @Override
    public DataResponse<List<WordDto>> getAllWords() {
        final List<WordDto> wordDtos = GlobalHelper.listDtoConverter(modelMapper,
                wordRepository.findAll(), WordDto.class);

        return new SuccessDataResponse<>(wordDtos, ResultMessages.EMPTY);
    }

    @Override
    public DataResponse<WordDto> getWordById(final UUID id) {
        final Word word = findById(id);

        return new SuccessDataResponse<>(modelMapper.map(word, WordDto.class), ResultMessages.EMPTY);
    }

    @Override
    public DataResponse<WordDto> createWord(final WordPostRequest wordPostRequest) {
        GlobalHelper.checkIfAlreadyExist(wordRepository.findByTextAndLanguage(wordPostRequest.getText(), wordPostRequest.getLanguage()));

        final Word word = new Word();

        word.setText(wordPostRequest.getText());
        word.setLanguage(wordPostRequest.getLanguage());

        return new SuccessDataResponse<>(modelMapper.map(wordRepository.save(word), WordDto.class), ResultMessages.SUCCESS_CREATE);
    }

    @Override
    public DataResponse<WordDto> updateWordById(final UUID id, final WordPutRequest wordPutRequest) {
        final Word wordToUpdate = findById(id);

        GlobalHelper.checkIfAlreadyExist(wordRepository.findByTextAndLanguage(wordPutRequest.getText(), wordPutRequest.getLanguage()));

        wordToUpdate.setText(wordPutRequest.getText());
        wordToUpdate.setLanguage(wordPutRequest.getLanguage());

        return new SuccessDataResponse<>(modelMapper.map(wordRepository.save(wordToUpdate), WordDto.class), ResultMessages.SUCCESS_UPDATE);
    }

    @Override
    public DataResponse<GameDto> addWordToGameByGameId(UUID gameId, WordPostGameRequest wordPostGameRequest) {
        final Game game = gameService.findGameById(gameId);

        wordPostGameRequest.getWordIds().forEach(wordId -> {
            if(checkAnySameWordInGame(game, UUID.fromString(wordId))){
                final Word word = findById(UUID.fromString(wordId));

                word.getGames().add(game);
                wordRepository.save(word);
            }
        });

        return new SuccessDataResponse<>(modelMapper.map(game, GameDto.class), ResultMessages.SUCCESS_CREATE);
    }

    @Override
    public BaseResponse deleteWordById(final UUID id) {
        final Word word = findById(id);

        wordRepository.delete(word);

        return new SuccessResponse(ResultMessages.SUCCESS_DELETE);
    }

    protected Word findById(UUID id){
        return wordRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(ResultMessages.NOT_FOUND_DATA));
    }

    protected boolean checkAnySameWordInGame(final Game game, final UUID wordId){
        return game.getWords().stream().anyMatch(gameWord -> gameWord.getId().equals(wordId));
    }
}

package com.wordrace.service.impl;

import com.wordrace.constant.ResultMessages;
import com.wordrace.dto.GameDto;
import com.wordrace.dto.RoomDto;
import com.wordrace.dto.UserDto;
import com.wordrace.dto.WordDto;
import com.wordrace.exception.EntityNotFoundException;
import com.wordrace.model.*;
import com.wordrace.repository.GameRepository;
import com.wordrace.repository.RoomRepository;
import com.wordrace.request.game.GamePostRequest;
import com.wordrace.request.game.GamePostWordRequest;
import com.wordrace.request.game.GamePutRequest;
import com.wordrace.request.word.WordPostRequest;
import com.wordrace.result.DataResult;
import com.wordrace.result.Result;
import com.wordrace.result.SuccessDataResult;
import com.wordrace.result.SuccessResult;
import com.wordrace.service.GameService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final RoomRepository roomRepository;
    private final ModelMapper modelMapper;

    public GameServiceImpl(GameRepository gameRepository, RoomRepository roomRepository, ModelMapper modelMapper) {
        this.gameRepository = gameRepository;
        this.roomRepository = roomRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public DataResult<List<GameDto>> getAllGames() {
        final List<GameDto> gameDtos = gameRepository.findAll()
                .stream()
                .map(game -> modelMapper.map(game, GameDto.class))
                .toList();
        return new SuccessDataResult<>(gameDtos, ResultMessages.EMPTY);
    }

    @Override
    public DataResult<GameDto> getGameById(Long id) {
        final Game game = findById(id);
        GameDto gameDto = modelMapper.map(game, GameDto.class);
        return new SuccessDataResult<>(gameDto, ResultMessages.EMPTY);
    }

    @Override
    public DataResult<List<WordDto>> getAllWordsByGameId(Long gameId) {
        final Game game = findById(gameId);
        final List<WordDto> wordDtos = game.getWords()
                .stream()
                .map(word-> modelMapper.map(word, WordDto.class))
                .toList();
        return new SuccessDataResult<>(wordDtos, ResultMessages.EMPTY);
    }

    @Override
    public DataResult<RoomDto> getRoomByGameId(Long gameId) {
        final Game game = findById(gameId);
        final Room room = game.getRoom();
        RoomDto roomDto = modelMapper.map(room, RoomDto.class);
        return new SuccessDataResult<>(roomDto, ResultMessages.EMPTY);
    }

    @Override
    public DataResult<List<UserDto>> getAllUsersByGameId(Long gameId) {
        final Game game = findById(gameId);
        final Room room = game.getRoom();
        List<UserDto> userDtos = room.getUsers()
                .stream()
                .map(user-> modelMapper.map(user, UserDto.class))
                .toList();
        return new SuccessDataResult<>(userDtos, ResultMessages.EMPTY);
    }

    @Override
    public DataResult<GameDto> createGame(GamePostRequest gamePostRequest) {
        final Room room = findRoomById(gamePostRequest.getRoomId());
        final Game game = new Game();
        game.setRoom(room);
        GameDto gameDto = modelMapper.map(gameRepository.save(game), GameDto.class);
        return new SuccessDataResult<>(gameDto, ResultMessages.SUCCESS_CREATE);
    }

    @Override
    public DataResult<GameDto> addWordToGameByGameId(Long gameId, GamePostWordRequest gamePostWordRequest) {
        final Game game = findById(gameId);
        Word word = new Word();
        gamePostWordRequest.getWords()
                .forEach(wordPostRequest -> {
                    game.getWords().forEach(gameWord->{
                        if(!gameWord.getText().equals(wordPostRequest.getText())){
                            word.setText(wordPostRequest.getText());
                            word.setLanguage(wordPostRequest.getLanguage());
                            game.getWords().add(word);
                        }
                    });
                });
        GameDto gameDto = modelMapper.map(gameRepository.save(game), GameDto.class);
        return new SuccessDataResult<>(gameDto, ResultMessages.EMPTY);
    }

    @Override
    public DataResult<GameDto> updateTotalScoreByGameId(Long gameId, GamePutRequest gamePutRequest) {
        final Game game = findById(gameId);
        game.setTotalScore(gamePutRequest.getTotalScore());
        GameDto gameDto = modelMapper.map(gameRepository.save(game), GameDto.class);
        return new SuccessDataResult<>(gameDto, ResultMessages.SUCCESS_UPDATE);
    }

    @Override
    public Result deleteGameById(Long id) {
        final Game game = findById(id);
        gameRepository.delete(game);
        return new SuccessResult(ResultMessages.SUCCESS_DELETE);
    }

    private Game findById(Long id){
        return gameRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ResultMessages.NOT_FOUND_DATA));
    }

    private Room findRoomById(Long id){
        return roomRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(ResultMessages.NOT_FOUND_DATA));
    }
}

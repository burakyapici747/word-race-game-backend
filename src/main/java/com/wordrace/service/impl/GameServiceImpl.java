package com.wordrace.service.impl;

import com.wordrace.constant.ResultMessages;
import com.wordrace.exception.EntityNotFoundException;
import com.wordrace.model.*;
import com.wordrace.repository.GameRepository;
import com.wordrace.repository.RoomRepository;
import com.wordrace.request.game.GamePostRequest;
import com.wordrace.request.game.GamePostWordRequest;
import com.wordrace.request.game.GamePutRequest;
import com.wordrace.result.DataResult;
import com.wordrace.result.Result;
import com.wordrace.result.SuccessDataResult;
import com.wordrace.result.SuccessResult;
import com.wordrace.service.GameService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final RoomRepository roomRepository;

    public GameServiceImpl(GameRepository gameRepository, RoomRepository roomRepository) {
        this.gameRepository = gameRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public DataResult<List<Game>> getAllGames() {
        final List<Game> games = new ArrayList<>(gameRepository.findAll());
        return new SuccessDataResult<>(games, ResultMessages.EMPTY);
    }

    @Override
    public DataResult<Game> getGameById(Long id) {
        final Game game = findById(id);
        return new SuccessDataResult<>(game, ResultMessages.EMPTY);
    }

    @Override
    public DataResult<List<Word>> getAllWordsByGameId(Long gameId) {
        final Game game = findById(gameId);
        List<Word> words = game.getWords();
        return new SuccessDataResult<>(words, ResultMessages.EMPTY);
    }

    @Override
    public DataResult<Room> getRoomByGameId(Long gameId) {
        final Game game = findById(gameId);
        final Room room = game.getRoom();
        return new SuccessDataResult<>(room, ResultMessages.EMPTY);
    }

    @Override
    public DataResult<List<User>> getAllUsersByGameId(Long gameId) {
        final Game game = findById(gameId);
        final Room room = game.getRoom();
        List<User> users = room.getUsers();
        return new SuccessDataResult<>(users, ResultMessages.EMPTY);
    }

    @Override
    public DataResult<Game> createGame(GamePostRequest gamePostRequest) {
        final Room room = findRoomById(gamePostRequest.getRoomId());
        final Game gameToCreate = new Game();
        gameToCreate.setRoom(room);
        return new SuccessDataResult<>(gameToCreate, ResultMessages.SUCCESS_CREATE);
    }

    @Override
    public DataResult<Game> addWordToGameByGameId(Long gameId, GamePostWordRequest gamePostWordRequest) {
        //TODO: Buradaki word modeli için game listesine herhangi bir ekleme yapılmadı.
        // Sistem değiştirilebilir.
        final Game game = findById(gameId);
        List<Word> words = new ArrayList<>();
        gamePostWordRequest.getWords().forEach(word -> {
            Word newWord = new Word();
            newWord.setText(word.getText());
            newWord.setLanguage(word.getLanguage());
            words.add(newWord);
        });
        game.setWords(words);
        return new SuccessDataResult<>(gameRepository.save(game), ResultMessages.EMPTY);
    }

    @Override
    public DataResult<Game> updateTotalScoreByGameId(Long gameId, GamePutRequest gamePutRequest) {
        final Game game = findById(gameId);
        game.setTotalScore(gamePutRequest.getTotalScore());
        return new SuccessDataResult<>(gameRepository.save(game), ResultMessages.SUCCESS_UPDATE);
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

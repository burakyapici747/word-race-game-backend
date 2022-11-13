package com.wordrace.service.impl;

import com.wordrace.constant.ResultMessages;
import com.wordrace.constant.RoomMessages;
import com.wordrace.model.*;
import com.wordrace.repository.GameRepository;
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

    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
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
    public DataResult<Game> createGame(Game game) {
        final Game gameToCreate = gameRepository.save(game);
        return new SuccessDataResult<>(gameToCreate, ResultMessages.SUCCESS_CREATE);
    }

    @Override
    public DataResult<Game> addWordToGameByGameId(List<Word> words, Long gameId) {
        final Game game = findById(gameId);
        game.setWords(words);
        return new SuccessDataResult<>(game, ResultMessages.EMPTY);
    }

    @Override
    public DataResult<Game> updateTotalScoreByGameId(Long gameId, int totalScore) {
        final Game game = findById(gameId);
        game.setTotalScore(totalScore);
        return new SuccessDataResult<>(game, ResultMessages.SUCCESS_UPDATE);
    }

    @Override
    public Result deleteGameById(Long id) {
        final Game game = findById(id);
        gameRepository.delete(game);
        return new SuccessResult(ResultMessages.SUCCESS_DELETE);
    }

    private Game findById(Long id){
        Optional<Game> gameOptional = gameRepository.findById(id);
        return gameOptional.get();
    }
}

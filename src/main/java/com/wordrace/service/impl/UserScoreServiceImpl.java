package com.wordrace.service.impl;

import com.wordrace.constant.ResultMessages;
import com.wordrace.dto.UserScoreDto;
import com.wordrace.exception.EntityNotFoundException;
import com.wordrace.model.Game;
import com.wordrace.model.User;
import com.wordrace.model.UserScore;
import com.wordrace.repository.GameRepository;
import com.wordrace.repository.UserRepository;
import com.wordrace.repository.UserScoreRepository;
import com.wordrace.request.userscore.UserScorePostRequest;
import com.wordrace.request.userscore.UserScorePutRequest;
import com.wordrace.result.DataResult;
import com.wordrace.result.Result;
import com.wordrace.result.SuccessDataResult;
import com.wordrace.result.SuccessResult;
import com.wordrace.service.UserScoreService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class UserScoreServiceImpl implements UserScoreService {

    private final UserScoreRepository userScoreRepository;
    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    private ModelMapper modelMapper;

    public UserScoreServiceImpl(UserScoreRepository userScoreRepository, GameRepository gameRepository, UserRepository userRepository) {
        this.userScoreRepository = userScoreRepository;
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
    }

    @Override
    public DataResult<List<UserScoreDto>> getAllUserScoresByGameId(Long gameId) {
        List<UserScoreDto> userScoreDtos = findUserScoreByGameId(gameId)
                .stream().map(userScore -> modelMapper.map(userScore, UserScoreDto.class))
                .toList();

        return new SuccessDataResult<>(userScoreDtos, ResultMessages.EMPTY);
    }

    @Override
    public DataResult<UserScoreDto> getUserScoreByUserIdAndGameId(Long userId, Long gameId) {
        return new SuccessDataResult<>(modelMapper.map(findUserScoreByUserIdAndGameId(userId, gameId), UserScoreDto.class), ResultMessages.EMPTY);
    }

    @Override
    public DataResult<UserScoreDto> createUserScore(UserScorePostRequest userScorePostRequest) {
        UserScore userScore = new UserScore();
        final User user = findUserById(userScorePostRequest.getUserId());
        final Game game = findGameById(userScorePostRequest.getGameId());
        userScore.setUser(user);
        userScore.setGame(game);
        userScore.setScore(userScorePostRequest.getScore());
        return new SuccessDataResult<>(modelMapper.map(userScoreRepository.save(userScore), UserScoreDto.class), ResultMessages.SUCCESS_CREATE);
    }

    @Override
    public DataResult<UserScoreDto> updateUserScore(UserScorePutRequest userScorePutRequest) {
        UserScore userScore = findUserScoreByUserIdAndGameId(userScorePutRequest.getUserId(), userScorePutRequest.getGameId());
        userScore.setScore(userScorePutRequest.getScore());
        return new SuccessDataResult<>(modelMapper.map(userScoreRepository.save(userScore), UserScoreDto.class), ResultMessages.SUCCESS_UPDATE);
    }

    @Override
    public Result deleteUserScoreByUserId(Long userId) {
        final UserScore userScore = findUserScoreById(userId);
        userScoreRepository.delete(userScore);
        return new SuccessResult(ResultMessages.SUCCESS_DELETE);
    }

    @Override
    public Result deleteUserScoreByGameId(Long gameId) {
        final List<UserScore> userScores = findUserScoreByGameId(gameId);
        userScoreRepository.deleteAll(userScores);
        return new SuccessResult(ResultMessages.SUCCESS_DELETE);
    }

    @Override
    public Result deleteUserScoreByUserAndGameId(Long userId, Long gameId) {
        final UserScore userScore = findUserScoreByUserIdAndGameId(userId, gameId);
        userScoreRepository.delete(userScore);
        return new SuccessResult(ResultMessages.SUCCESS_DELETE);
    }

    private UserScore findUserScoreById(Long id){
        return userScoreRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(ResultMessages.NOT_FOUND_DATA));
    }

    private List<UserScore> findUserScoreByGameId(Long gameId){
        return userScoreRepository.findByGameId(gameId)
                .orElseThrow(()-> new EntityNotFoundException(ResultMessages.NOT_FOUND_DATA));
    }

    private UserScore findUserScoreByUserIdAndGameId(Long userId, Long gameId){
        return userScoreRepository.findByUserIdAndGameId(userId, gameId)
                .orElseThrow(()-> new EntityNotFoundException(ResultMessages.NOT_FOUND_DATA));
    }

    private Game findGameById(Long id){
        return gameRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(ResultMessages.NOT_FOUND_DATA));
    }

    private User findUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(ResultMessages.NOT_FOUND_DATA));
    }

}

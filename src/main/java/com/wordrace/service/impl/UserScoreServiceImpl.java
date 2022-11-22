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

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserScoreServiceImpl implements UserScoreService {

    private final UserScoreRepository userScoreRepository;
    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    public UserScoreServiceImpl(UserScoreRepository userScoreRepository,
                                GameRepository gameRepository,
                                UserRepository userRepository,
                                ModelMapper modelMapper) {
        this.userScoreRepository = userScoreRepository;
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public DataResult<List<UserScoreDto>> getAllUserScoresByGameId(UUID gameId) {
        List<UserScoreDto> userScoreDtos = findUserScoreByGameId(gameId)
                .stream().map(userScore -> modelMapper.map(userScore, UserScoreDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(userScoreDtos, ResultMessages.EMPTY);
    }

    @Override
    public DataResult<List<UserScoreDto>> getAllUserScoresByUserId(UUID userId) {
       final  List<UserScoreDto> userScoreDtos = findUserScoreByUserId(userId)
                .stream().map(userScore -> modelMapper.map(userScore, UserScoreDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(userScoreDtos, ResultMessages.EMPTY);
    }

    @Override
    public DataResult<UserScoreDto> getUserScoreByUserIdAndGameId(UUID userId, UUID gameId) {
        return new SuccessDataResult<>
                (modelMapper.map(findUserScoreByUserIdAndGameId(userId, gameId), UserScoreDto.class),
                        ResultMessages.EMPTY);
    }

    @Override
    public DataResult<UserScoreDto> createUserScore(UserScorePostRequest userScorePostRequest) {
        final UserScore userScore = new UserScore();
        final User user = findUserById(UUID.fromString(userScorePostRequest.getUserId()));
        final Game game = findGameById(UUID.fromString(userScorePostRequest.getGameId()));

        userScore.setUser(user);
        userScore.setGame(game);
        userScore.setScore(userScorePostRequest.getScore());

        return new SuccessDataResult<>(modelMapper.map(userScoreRepository.save(userScore), UserScoreDto.class), ResultMessages.SUCCESS_CREATE);
    }

    @Override
    public DataResult<UserScoreDto> updateUserScore(UserScorePutRequest userScorePutRequest) {
        final UserScore userScore = findUserScoreByUserIdAndGameId(UUID.fromString(userScorePutRequest.getUserId()),
                UUID.fromString(userScorePutRequest.getGameId()));

        userScore.setScore(userScorePutRequest.getScore());

        return new SuccessDataResult<>(modelMapper.map(userScoreRepository.save(userScore), UserScoreDto.class), ResultMessages.SUCCESS_UPDATE);
    }

    @Override
    public Result deleteUserScoreByUserId(UUID userId) {
        final List<UserScore> userScore = findUserScoreByUserId(userId);

        userScoreRepository.deleteAll(userScore);

        return new SuccessResult(ResultMessages.SUCCESS_DELETE);
    }

    @Override
    public Result deleteUserScoreByGameId(UUID gameId) {
        final List<UserScore> userScores = findUserScoreByGameId(gameId);

        userScoreRepository.deleteAll(userScores);

        return new SuccessResult(ResultMessages.SUCCESS_DELETE);
    }

    @Override
    public Result deleteUserScoreByUserIdAndGameId(UUID userId, UUID gameId) {
        final UserScore userScore = findUserScoreByUserIdAndGameId(userId, gameId);
        userScoreRepository.delete(userScore);
        return new SuccessResult(ResultMessages.SUCCESS_DELETE);
    }

    private UserScore findUserScoreById(UUID id){
        return userScoreRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(ResultMessages.NOT_FOUND_DATA));
    }

    private List<UserScore> findUserScoreByGameId(UUID gameId){
        return userScoreRepository.findByGameId(gameId)
                .orElseThrow(()-> new EntityNotFoundException(ResultMessages.NOT_FOUND_DATA));
    }

    private List<UserScore> findUserScoreByUserId(UUID userId){
        return userScoreRepository.findByUserId(userId)
                .orElseThrow(()-> new EntityNotFoundException(ResultMessages.NOT_FOUND_DATA));
    }

    private UserScore findUserScoreByUserIdAndGameId(UUID userId, UUID gameId){
        return userScoreRepository.findByUserIdAndGameId(userId, gameId)
                .orElseThrow(()-> new EntityNotFoundException(ResultMessages.NOT_FOUND_DATA));
    }

    private Game findGameById(UUID id){
        return gameRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(ResultMessages.NOT_FOUND_DATA));
    }

    private User findUserById(UUID id){
        return userRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(ResultMessages.NOT_FOUND_DATA));
    }

}

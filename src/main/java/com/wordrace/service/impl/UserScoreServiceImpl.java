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
import com.wordrace.util.GlobalHelper;
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
    public DataResult<List<UserScoreDto>> getAllUserScoresByGameId(final UUID gameId) {
        List<UserScoreDto> userScoreDtos = GlobalHelper.listDtoConverter(modelMapper,
                findUserScoreByGameId(gameId), UserScoreDto.class);

        return new SuccessDataResult<>(userScoreDtos, ResultMessages.EMPTY);
    }

    @Override
    public DataResult<List<UserScoreDto>> getAllUserScoresByUserId(final UUID userId) {
       final  List<UserScoreDto> userScoreDtos = GlobalHelper.listDtoConverter(modelMapper,
               findUserScoreByUserId(userId), UserScoreDto.class);

        return new SuccessDataResult<>(userScoreDtos, ResultMessages.EMPTY);
    }

    @Override
    public DataResult<UserScoreDto> getUserScoreByUserIdAndGameId(final UUID userId, final UUID gameId) {
        return new SuccessDataResult<>
                (modelMapper.map(findUserScoreByUserIdAndGameId(userId, gameId), UserScoreDto.class),
                        ResultMessages.EMPTY);
    }

    @Override
    public DataResult<UserScoreDto> createUserScore(final UserScorePostRequest userScorePostRequest) {
        final UserScore userScore = new UserScore();
        final User user = findUserById(UUID.fromString(userScorePostRequest.getUserId()));
        final Game game = findGameById(UUID.fromString(userScorePostRequest.getGameId()));

        userScore.setUser(user);
        userScore.setGame(game);
        userScore.setScore(userScorePostRequest.getScore());

        return new SuccessDataResult<>(modelMapper.map(userScoreRepository.save(userScore), UserScoreDto.class), ResultMessages.SUCCESS_CREATE);
    }

    @Override
    public DataResult<UserScoreDto> updateUserScore(final UserScorePutRequest userScorePutRequest) {
        final UserScore userScore = findUserScoreByUserIdAndGameId(UUID.fromString(userScorePutRequest.getUserId()),
                UUID.fromString(userScorePutRequest.getGameId()));

        userScore.setScore(userScorePutRequest.getScore());

        return new SuccessDataResult<>(modelMapper.map(userScoreRepository.save(userScore), UserScoreDto.class), ResultMessages.SUCCESS_UPDATE);
    }

    @Override
    public Result deleteUserScoreByUserId(final UUID userId) {
        final List<UserScore> userScore = findUserScoreByUserId(userId);

        userScoreRepository.deleteAll(userScore);

        return new SuccessResult(ResultMessages.SUCCESS_DELETE);
    }

    @Override
    public Result deleteUserScoreByGameId(final UUID gameId) {
        final List<UserScore> userScores = findUserScoreByGameId(gameId);

        userScoreRepository.deleteAll(userScores);

        return new SuccessResult(ResultMessages.SUCCESS_DELETE);
    }

    @Override
    public Result deleteUserScoreByUserIdAndGameId(final UUID userId, final UUID gameId) {
        final UserScore userScore = findUserScoreByUserIdAndGameId(userId, gameId);

        userScoreRepository.delete(userScore);

        return new SuccessResult(ResultMessages.SUCCESS_DELETE);
    }

    private UserScore findUserScoreById(final UUID id){
        return userScoreRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(ResultMessages.NOT_FOUND_DATA));
    }

    private List<UserScore> findUserScoreByGameId(final UUID gameId){
        return userScoreRepository.findByGameId(gameId)
                .orElseThrow(()-> new EntityNotFoundException(ResultMessages.NOT_FOUND_DATA));
    }

    private List<UserScore> findUserScoreByUserId(final UUID userId){
        return userScoreRepository.findByUserId(userId)
                .orElseThrow(()-> new EntityNotFoundException(ResultMessages.NOT_FOUND_DATA));
    }

    private UserScore findUserScoreByUserIdAndGameId(final UUID userId, final UUID gameId){
        return userScoreRepository.findByUserIdAndGameId(userId, gameId)
                .orElseThrow(()-> new EntityNotFoundException(ResultMessages.NOT_FOUND_DATA));
    }

    private Game findGameById(final UUID id){
        return gameRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(ResultMessages.NOT_FOUND_DATA));
    }

    private User findUserById(final UUID id){
        return userRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(ResultMessages.NOT_FOUND_DATA));
    }

}

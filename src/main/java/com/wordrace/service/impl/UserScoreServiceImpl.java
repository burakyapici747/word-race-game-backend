package com.wordrace.service.impl;

import com.wordrace.constant.ResponseConstant;
import com.wordrace.dto.UserScoreDto;
import com.wordrace.exception.EntityNotFoundException;
import com.wordrace.model.Game;
import com.wordrace.model.User;
import com.wordrace.model.UserScore;
import com.wordrace.repository.UserScoreRepository;
import com.wordrace.api.request.userscore.UserScorePostRequest;
import com.wordrace.api.request.userscore.UserScorePutRequest;
import com.wordrace.api.response.DataResponse;
import com.wordrace.api.response.BaseResponse;
import com.wordrace.api.response.SuccessDataResponse;
import com.wordrace.api.response.SuccessResponse;
import com.wordrace.service.UserScoreService;
import com.wordrace.util.GlobalHelper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserScoreServiceImpl implements UserScoreService {

    private final GameServiceImpl gameService;
    private final UserServiceImpl userService;
    private final UserScoreRepository userScoreRepository;
    private final ModelMapper modelMapper;

    public UserScoreServiceImpl(GameServiceImpl gameService,
                                UserServiceImpl userService,
                                UserScoreRepository userScoreRepository,
                                ModelMapper modelMapper) {
        this.gameService = gameService;
        this.userService = userService;
        this.userScoreRepository = userScoreRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public DataResponse<List<UserScoreDto>> getAllUserScoresByGameId(final UUID gameId) {
        List<UserScoreDto> userScoreDtoList = GlobalHelper.listDtoConverter(modelMapper,
                findUserScoreByGameId(gameId), UserScoreDto.class);

        return new SuccessDataResponse<>(userScoreDtoList, ResponseConstant.EMPTY);
    }

    @Override
    public DataResponse<List<UserScoreDto>> getAllUserScoresByUserId(final UUID userId) {
       final  List<UserScoreDto> userScoreDtoList = GlobalHelper.listDtoConverter(modelMapper,
               findUserScoreByUserId(userId), UserScoreDto.class);

        return new SuccessDataResponse<>(userScoreDtoList, ResponseConstant.EMPTY);
    }

    @Override
    public DataResponse<UserScoreDto> getUserScoreByUserIdAndGameId(final UUID userId, final UUID gameId) {
        return new SuccessDataResponse<>
                (modelMapper.map(findUserScoreByUserIdAndGameId(userId, gameId), UserScoreDto.class),
                        ResponseConstant.EMPTY);
    }

    @Override
    public DataResponse<UserScoreDto> createUserScore(final UserScorePostRequest userScorePostRequest) {
        final UserScore userScore = new UserScore();
        final User user = userService.findUserById(UUID.fromString(userScorePostRequest.getUserId()));
        final Game game = gameService.findGameById(UUID.fromString(userScorePostRequest.getGameId()));

        userScore.setUser(user);
        userScore.setGame(game);
        userScore.setScore(userScorePostRequest.getScore());

        return new SuccessDataResponse<>(modelMapper.map(userScoreRepository.save(userScore), UserScoreDto.class), ResponseConstant.SUCCESS_CREATE);
    }

    @Override
    public DataResponse<UserScoreDto> updateUserScore(final UserScorePutRequest userScorePutRequest) {
        final UserScore userScore = findUserScoreByUserIdAndGameId(UUID.fromString(userScorePutRequest.getUserId()),
                UUID.fromString(userScorePutRequest.getGameId()));

        userScore.setScore(userScorePutRequest.getScore());

        return new SuccessDataResponse<>(modelMapper.map(userScoreRepository.save(userScore), UserScoreDto.class), ResponseConstant.SUCCESS_UPDATE);
    }

    @Override
    public BaseResponse deleteUserScoreByUserId(final UUID userId) {
        final List<UserScore> userScore = findUserScoreByUserId(userId);

        userScoreRepository.deleteAll(userScore);

        return new SuccessResponse(ResponseConstant.SUCCESS_DELETE);
    }

    @Override
    public BaseResponse deleteUserScoreByGameId(final UUID gameId) {
        final List<UserScore> userScores = findUserScoreByGameId(gameId);

        userScoreRepository.deleteAll(userScores);

        return new SuccessResponse(ResponseConstant.SUCCESS_DELETE);
    }

    @Override
    public BaseResponse deleteUserScoreByUserIdAndGameId(final UUID userId, final UUID gameId) {
        final UserScore userScore = findUserScoreByUserIdAndGameId(userId, gameId);

        userScoreRepository.delete(userScore);

        return new SuccessResponse(ResponseConstant.SUCCESS_DELETE);
    }

    protected UserScore findUserScoreById(final UUID id){
        return userScoreRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(ResponseConstant.NOT_FOUND_DATA));
    }

    protected List<UserScore> findUserScoreByGameId(final UUID gameId){
        return userScoreRepository.findByGameId(gameId)
                .orElseThrow(()-> new EntityNotFoundException(ResponseConstant.NOT_FOUND_DATA));
    }

    protected List<UserScore> findUserScoreByUserId(final UUID userId){
        return userScoreRepository.findByUserId(userId)
                .orElseThrow(()-> new EntityNotFoundException(ResponseConstant.NOT_FOUND_DATA));
    }

    protected UserScore findUserScoreByUserIdAndGameId(final UUID userId, final UUID gameId){
        return userScoreRepository.findByUserIdAndGameId(userId, gameId)
                .orElseThrow(()-> new EntityNotFoundException(ResponseConstant.NOT_FOUND_DATA));
    }

}

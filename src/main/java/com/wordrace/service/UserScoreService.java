package com.wordrace.service;

import com.wordrace.dto.UserScoreDto;
import com.wordrace.model.UserScore;
import com.wordrace.request.userscore.UserScorePostRequest;
import com.wordrace.request.userscore.UserScorePutRequest;
import com.wordrace.result.DataResult;
import com.wordrace.result.Result;

import java.util.List;

public interface UserScoreService {

    //GET OPERATIONS
    DataResult<List<UserScoreDto>> getAllUserScoresByGameId(Long gameId);
    DataResult<List<UserScoreDto>> getAllUserScoresByUserId(Long userId);
    DataResult<UserScoreDto> getUserScoreByUserIdAndGameId(Long userId, Long gameId);

    //POST OPERATIONS
    DataResult<UserScoreDto> createUserScore(UserScorePostRequest userScorePostRequest);

    //PUT OPERATIONS
    DataResult<UserScoreDto> updateUserScore(UserScorePutRequest userScorePutRequest);

    //DELETE OPERATIONS
    Result deleteUserScoreByUserId(Long userId);
    Result deleteUserScoreByGameId(Long gameId);
    Result deleteUserScoreByUserIdAndGameId(Long userId, Long gameId);

}
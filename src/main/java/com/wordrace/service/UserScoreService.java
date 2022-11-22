package com.wordrace.service;

import com.wordrace.dto.UserScoreDto;
import com.wordrace.model.UserScore;
import com.wordrace.request.userscore.UserScorePostRequest;
import com.wordrace.request.userscore.UserScorePutRequest;
import com.wordrace.result.DataResult;
import com.wordrace.result.Result;

import java.util.List;
import java.util.UUID;

public interface UserScoreService {
    DataResult<List<UserScoreDto>> getAllUserScoresByGameId(UUID gameId);
    DataResult<List<UserScoreDto>> getAllUserScoresByUserId(UUID userId);
    DataResult<UserScoreDto> getUserScoreByUserIdAndGameId(UUID userId, UUID gameId);
    DataResult<UserScoreDto> createUserScore(UserScorePostRequest userScorePostRequest);
    DataResult<UserScoreDto> updateUserScore(UserScorePutRequest userScorePutRequest);
    Result deleteUserScoreByUserId(UUID userId);
    Result deleteUserScoreByGameId(UUID gameId);
    Result deleteUserScoreByUserIdAndGameId(UUID userId, UUID gameId);
}
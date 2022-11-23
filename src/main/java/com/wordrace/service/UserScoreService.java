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
    DataResult<List<UserScoreDto>> getAllUserScoresByGameId(final UUID gameId);
    DataResult<List<UserScoreDto>> getAllUserScoresByUserId(final UUID userId);
    DataResult<UserScoreDto> getUserScoreByUserIdAndGameId(final UUID userId, final UUID gameId);
    DataResult<UserScoreDto> createUserScore(final UserScorePostRequest userScorePostRequest);
    DataResult<UserScoreDto> updateUserScore(final UserScorePutRequest userScorePutRequest);
    Result deleteUserScoreByUserId(final UUID userId);
    Result deleteUserScoreByGameId(final UUID gameId);
    Result deleteUserScoreByUserIdAndGameId(final UUID userId, final UUID gameId);
}
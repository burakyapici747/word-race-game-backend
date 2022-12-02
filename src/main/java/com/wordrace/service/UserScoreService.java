package com.wordrace.service;

import com.wordrace.dto.UserScoreDto;
import com.wordrace.api.request.userscore.UserScorePostRequest;
import com.wordrace.api.request.userscore.UserScorePutRequest;
import com.wordrace.api.response.DataResponse;
import com.wordrace.api.response.BaseResponse;

import java.util.List;
import java.util.UUID;

public interface UserScoreService {
    DataResponse<List<UserScoreDto>> getAllUserScoresByGameId(final UUID gameId);
    DataResponse<List<UserScoreDto>> getAllUserScoresByUserId(final UUID userId);
    DataResponse<UserScoreDto> getUserScoreByUserIdAndGameId(final UUID userId, final UUID gameId);
    DataResponse<UserScoreDto> createUserScore(final UserScorePostRequest userScorePostRequest);
    DataResponse<UserScoreDto> updateUserScore(final UserScorePutRequest userScorePutRequest);
    BaseResponse deleteUserScoreByUserId(final UUID userId);
    BaseResponse deleteUserScoreByGameId(final UUID gameId);
    BaseResponse deleteUserScoreByUserIdAndGameId(final UUID userId, final UUID gameId);
}
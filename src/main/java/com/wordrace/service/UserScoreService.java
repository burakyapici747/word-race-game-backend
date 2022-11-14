package com.wordrace.service;

import com.wordrace.model.UserScore;
import com.wordrace.request.userscore.UserScorePostRequest;
import com.wordrace.request.userscore.UserScorePutRequest;
import com.wordrace.result.DataResult;
import com.wordrace.result.Result;

import java.util.List;

public interface UserScoreService {

    //GET OPERATIONS
    DataResult<List<UserScore>> getAllUserScoresByGameId(Long gameId);
    DataResult<UserScore> getUserScoreByUserIdAndGameId(Long userId, Long gameId);

    //POST OPERATIONS
    DataResult<UserScore> createUserScore(UserScorePostRequest userScorePostRequest);

    //PUT OPERATIONS
    DataResult<UserScore> updateUserScore(UserScorePutRequest userScorePutRequest);

    //DELETE OPERATIONS
    Result deleteUserScoreByUserId(Long userId);
    Result deleteUserScoreByGameId(Long gameId);
    Result deleteUserScoreByUserAndGameId(Long userId, Long gameId);

}
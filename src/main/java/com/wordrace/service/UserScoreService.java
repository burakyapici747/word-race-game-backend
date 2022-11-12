package com.wordrace.service;

import com.wordrace.model.UserScore;
import com.wordrace.result.DataResult;

import java.util.List;
import java.util.Optional;

public interface UserScoreService {

    //GET OPERATIONS
    DataResult<List<UserScore>> getAllUserScoresByGameId(Long gameId);
    DataResult<UserScore> getUserScoreByUserAndGameId(Long userId, Long gameId);

    //POST OPERATIONS
    DataResult<UserScore> createUserScore(UserScore userScore);

    //PUT OPERATIONS
    DataResult<UserScore> updateUserScore(Long userId, Long gameId, int score);

    //DELETE OPERATIONS
    DataResult<Boolean> deleteUserScoreByUserId(Long userId);
    DataResult<Boolean> deleteUserScoreByGameId(Long gameId);
    DataResult<Boolean> deleteUserScoreByUserAndGameId(Long userId, Long gameId);

}
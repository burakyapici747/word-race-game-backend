package com.wordrace.service;

import com.wordrace.model.UserScore;

import java.util.List;
import java.util.Optional;

public interface UserScoreService {

    //GET OPERATIONS
    Optional<List<UserScore>> getAllUserScoresByGameId(Long gameId);
    Optional<UserScore> getUserScoreByUserAndGameId(Long userId, Long gameId);

    //POST OPERATIONS
    Optional<UserScore> createUserScore(UserScore userScore);

    //PUT OPERATIONS
    Optional<UserScore> updateUserScore(Long userId, Long gameId, int score);

    //DELETE OPERATIONS
    boolean deleteUserScoreByUserId(Long userId);
    boolean deleteUserScoreByGameId(Long gameId);
    boolean deleteUserScoreByUserAndGameId(Long userId, Long gameId);

}
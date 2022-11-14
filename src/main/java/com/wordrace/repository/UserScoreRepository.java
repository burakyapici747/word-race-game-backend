package com.wordrace.repository;

import com.wordrace.model.UserScore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserScoreRepository extends JpaRepository<UserScore, Long> {
    Optional<List<UserScore>> findByGameId(Long gameId);
    Optional<UserScore> findByUserIdAndGameId(Long userId, Long gameId);
}

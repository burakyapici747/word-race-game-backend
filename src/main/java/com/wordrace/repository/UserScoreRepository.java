package com.wordrace.repository;

import com.wordrace.model.UserScore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserScoreRepository extends JpaRepository<UserScore, UUID> {
    Optional<List<UserScore>> findByGameId(UUID gameId);
    Optional<List<UserScore>> findByUserId(UUID userId);
    Optional<UserScore> findByUserIdAndGameId(UUID userId, UUID gameId);
}

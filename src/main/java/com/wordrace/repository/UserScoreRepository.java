package com.wordrace.repository;

import com.wordrace.model.UserScore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserScoreRepository extends JpaRepository<UserScore, Long> {
}

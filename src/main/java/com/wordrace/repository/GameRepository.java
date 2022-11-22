package com.wordrace.repository;

import com.wordrace.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GameRepository extends JpaRepository<Game, UUID> {
}

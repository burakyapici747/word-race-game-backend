package com.wordrace.service;


import com.wordrace.model.Game;
import com.wordrace.model.Room;
import com.wordrace.model.User;
import com.wordrace.model.Word;

import java.util.List;
import java.util.Optional;

public interface GameService {

    //Get Operations
    Optional<List<Game>> getAllGames();
    Optional<Game> getGameById(Long id);
    Optional<Word> getWordByGameId(Long gameId);
    Optional<Room> getRoomByGameId(Long gameId);
    Optional<List<User>> getUsersByGameId(Long gameId);

    //Post Operations
    Optional<Game> createGame(Long roomId);
    Optional<Word> addWordToGame(List<Word> words);

    //Put Operations
    Optional<Game> updateTotalScoreById(Long id);

    //Delete Operations
    boolean deleteGameById(Long id);
}

package com.wordrace.service.impl;


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
    Optional<Word> getWordByGameId(Long id);
    Optional<Room> getRoomByGameId(Long id);
    Optional<List<User>> getUsersByGameId(Long id);

    //Post Operations
    Optional<Game> createGame(Long roomId);
    Optional<Word> addWordToGame(List<Word> words);

    //Put Operations
    Optional<Game> updateTotalScoreById(Long id);

    //Delete Operations
    Optional<Boolean> deleteGameById(Long id);
}

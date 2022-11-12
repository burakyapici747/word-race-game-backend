package com.wordrace.service;


import com.wordrace.model.Game;
import com.wordrace.model.Room;
import com.wordrace.model.User;
import com.wordrace.model.Word;
import com.wordrace.result.DataResult;
import com.wordrace.result.Result;

import java.util.List;

public interface GameService {

    //Get Operations
    DataResult<List<Game>> getAllGames();
    DataResult<Game> getGameById(Long id);
    DataResult<List<Word>> getAllWordsByGameId(Long gameId);
    DataResult<Room> getRoomByGameId(Long gameId);
    DataResult<List<User>> getAllUsersByGameId(Long gameId);

    //Post Operations
    DataResult<Game> createGame(Long roomId);
    DataResult<Word> addWordToGame(List<Word> words);

    //Put Operations
    DataResult<Game> updateTotalScoreById(Long id, int totalScore);

    //Delete Operations
    Result deleteGameById(Long id);

}

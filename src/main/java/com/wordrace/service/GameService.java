package com.wordrace.service;


import com.wordrace.dto.GameDto;
import com.wordrace.dto.RoomDto;
import com.wordrace.dto.UserDto;
import com.wordrace.dto.WordDto;
import com.wordrace.model.Game;
import com.wordrace.model.Room;
import com.wordrace.model.User;
import com.wordrace.model.Word;
import com.wordrace.request.game.GamePostRequest;
import com.wordrace.request.game.GamePostWordRequest;
import com.wordrace.request.game.GamePutRequest;
import com.wordrace.result.DataResult;
import com.wordrace.result.Result;

import java.util.List;

public interface GameService {

    //Get Operations
    DataResult<List<GameDto>> getAllGames();
    DataResult<GameDto> getGameById(Long id);
    DataResult<List<WordDto>> getAllWordsByGameId(Long gameId);
    DataResult<RoomDto> getRoomByGameId(Long gameId);
    DataResult<List<UserDto>> getAllUsersByGameId(Long gameId);

    //Post Operations
    DataResult<GameDto> createGame(GamePostRequest gamePostRequest);
    DataResult<GameDto> addWordToGameByGameId(Long gameId, GamePostWordRequest gamePostWordRequest);

    //Put Operations
    DataResult<GameDto> updateTotalScoreByGameId(Long gameId, GamePutRequest gamePutRequest);

    //Delete Operations
    Result deleteGameById(Long id);

}

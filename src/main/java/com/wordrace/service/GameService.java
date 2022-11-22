package com.wordrace.service;


import com.wordrace.dto.GameDto;
import com.wordrace.dto.RoomDto;
import com.wordrace.dto.UserDto;
import com.wordrace.dto.WordDto;
import com.wordrace.request.game.GamePostRequest;
import com.wordrace.request.game.GamePostWordRequest;
import com.wordrace.request.game.GamePutRequest;
import com.wordrace.result.DataResult;
import com.wordrace.result.Result;

import java.util.List;
import java.util.UUID;

public interface GameService {
    DataResult<List<GameDto>> getAllGames();
    DataResult<GameDto> getGameById(UUID id);
    DataResult<List<WordDto>> getAllWordsByGameId(UUID gameId);
    DataResult<RoomDto> getRoomByGameId(UUID gameId);
    DataResult<List<UserDto>> getAllUsersByGameId(UUID gameId);
    DataResult<GameDto> createGame(GamePostRequest gamePostRequest);
    DataResult<GameDto> addWordToGameByGameId(UUID gameId, GamePostWordRequest gamePostWordRequest);
    DataResult<GameDto> updateTotalScoreByGameId(UUID gameId, GamePutRequest gamePutRequest);
    Result deleteGameById(UUID id);
}

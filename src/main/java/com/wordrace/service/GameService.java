package com.wordrace.service;


import com.wordrace.dto.GameDto;
import com.wordrace.dto.RoomDto;
import com.wordrace.dto.UserDto;
import com.wordrace.dto.WordDto;
import com.wordrace.request.game.GamePostRequest;
import com.wordrace.request.game.GamePutRequest;
import com.wordrace.result.DataResult;
import com.wordrace.result.Result;

import java.util.List;
import java.util.UUID;

public interface GameService {
    DataResult<List<GameDto>> getAllGames();
    DataResult<GameDto> getGameById(final UUID id);
    DataResult<List<WordDto>> getAllWordsByGameId(final UUID gameId);
    DataResult<RoomDto> getRoomByGameId(final UUID gameId);
    DataResult<List<UserDto>> getAllUsersByGameId(final UUID gameId);
    DataResult<GameDto> createGame(final GamePostRequest gamePostRequest);
    DataResult<GameDto> updateTotalScoreByGameId(final UUID gameId, final GamePutRequest gamePutRequest);
    Result deleteGameById(final UUID id);
}

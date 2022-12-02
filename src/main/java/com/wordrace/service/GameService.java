package com.wordrace.service;


import com.wordrace.dto.GameDto;
import com.wordrace.dto.RoomDto;
import com.wordrace.dto.UserDto;
import com.wordrace.dto.WordDto;
import com.wordrace.api.request.game.GamePostRequest;
import com.wordrace.api.request.game.GamePutRequest;
import com.wordrace.api.response.DataResponse;
import com.wordrace.api.response.BaseResponse;

import java.util.List;
import java.util.UUID;

public interface GameService {
    DataResponse<List<GameDto>> getAllGames();
    DataResponse<GameDto> getGameById(final UUID id);
    DataResponse<List<WordDto>> getAllWordsByGameId(final UUID gameId);
    DataResponse<RoomDto> getRoomByGameId(final UUID gameId);
    DataResponse<List<UserDto>> getAllUsersByGameId(final UUID gameId);
    DataResponse<GameDto> createGame(final GamePostRequest gamePostRequest);
    DataResponse<GameDto> updateTotalScoreByGameId(final UUID gameId, final GamePutRequest gamePutRequest);
    BaseResponse deleteGameById(final UUID id);
}

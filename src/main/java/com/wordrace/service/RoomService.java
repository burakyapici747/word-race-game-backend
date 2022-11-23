package com.wordrace.service;

import com.wordrace.dto.GameDto;
import com.wordrace.dto.RoomDto;
import com.wordrace.dto.UserDto;
import com.wordrace.dto.WordDto;
import com.wordrace.request.room.RoomPostRequest;
import com.wordrace.request.room.RoomPutRequest;
import com.wordrace.result.*;
import java.util.List;
import java.util.UUID;

public interface RoomService {
    DataResult<List<RoomDto>> getAllRooms();
    DataResult<RoomDto> getRoomById(final UUID id);
    DataResult<GameDto> getGameByRoomId(final UUID roomId);
    DataResult<List<WordDto>> getWordsByRoomId(final UUID roomId);
    DataResult<List<UserDto>> getUsersByRoomId(final UUID roomId);
    DataResult<RoomDto> createRoom(final RoomPostRequest roomPostRequest);
    DataResult<RoomDto> updateRoomById(final UUID id, final RoomPutRequest roomPutRequest);
    Result deleteRoomById(final UUID id);
}

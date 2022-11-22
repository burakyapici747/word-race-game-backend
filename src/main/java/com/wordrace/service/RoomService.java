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
    DataResult<RoomDto> getRoomById(UUID id);
    DataResult<GameDto> getGameByRoomId(UUID roomId);
    DataResult<List<WordDto>> getWordsByRoomId(UUID roomId);
    DataResult<List<UserDto>> getUsersByRoomId(UUID roomId);
    DataResult<RoomDto> createRoom(RoomPostRequest roomPostRequest);
    DataResult<RoomDto> updateRoomById(UUID id, RoomPutRequest roomPutRequest);
    Result deleteRoomById(UUID id);
}

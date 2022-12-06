package com.wordrace.service;

import com.wordrace.api.response.BaseResponse;
import com.wordrace.api.response.DataResponse;
import com.wordrace.dto.GameDto;
import com.wordrace.dto.RoomDto;
import com.wordrace.dto.UserDto;
import com.wordrace.dto.WordDto;
import com.wordrace.api.request.room.RoomPostRequest;
import com.wordrace.api.request.room.RoomPutRequest;
import java.util.List;
import java.util.UUID;

public interface RoomService {
    DataResponse<List<RoomDto>> getAllRooms();
    DataResponse<RoomDto> getRoomById(final UUID id);
    DataResponse<GameDto> getGameByRoomId(final UUID roomId);
    DataResponse<List<WordDto>> getWordsByRoomId(final UUID roomId);
    DataResponse<List<UserDto>> getUsersByRoomId(final UUID roomId);
    DataResponse<RoomDto> createRoom(final RoomPostRequest roomPostRequest);
    DataResponse<RoomDto> updateRoomById(final UUID id, final RoomPutRequest roomPutRequest);
    BaseResponse deleteRoomById(final UUID id);
}

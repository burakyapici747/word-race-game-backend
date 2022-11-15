package com.wordrace.service;

import com.wordrace.dto.GameDto;
import com.wordrace.dto.RoomDto;
import com.wordrace.dto.UserDto;
import com.wordrace.dto.WordDto;
import com.wordrace.request.room.RoomPostRequest;
import com.wordrace.request.room.RoomPutRequest;
import com.wordrace.result.*;
import java.util.List;

public interface RoomService {

    //Get Operations
    DataResult<List<RoomDto>> getAllRooms();
    DataResult<RoomDto> getRoomById(Long id);
    DataResult<GameDto> getGameByRoomId(Long roomId);
    DataResult<List<WordDto>> getWordsByRoomId(Long roomId);
    DataResult<List<UserDto>> getUsersByRoomId(Long roomId);

    //Post Operations
    DataResult<RoomDto> createRoom(RoomPostRequest roomPostRequest);

    //Put Operations
    DataResult<RoomDto> updateRoomById(Long id, RoomPutRequest roomPutRequest);

    //Delete Operations
    Result deleteRoomById(Long id);

}

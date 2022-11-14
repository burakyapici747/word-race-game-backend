package com.wordrace.service;

import com.wordrace.model.Game;
import com.wordrace.model.Room;
import com.wordrace.model.Word;
import com.wordrace.request.room.RoomPostRequest;
import com.wordrace.request.room.RoomPutRequest;
import com.wordrace.result.*;
import java.util.List;

public interface RoomService {

    //Get Operations
    DataResult<List<Room>> getAllRooms();
    DataResult<Room> getRoomById(Long id);
    DataResult<Game> getGameByRoomId(Long roomId);
    DataResult<List<Word>> getWordsByRoomId(Long roomId);

    //Post Operations
    DataResult<Room> createRoom(RoomPostRequest roomPostRequest);

    //Put Operations
    DataResult<Room> updateRoomById(Long id, RoomPutRequest roomPutRequest);

    //Delete Operations
    Result deleteRoomById(Long id);

}

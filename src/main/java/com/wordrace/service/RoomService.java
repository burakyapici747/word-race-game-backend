package com.wordrace.service;

import com.wordrace.model.Game;
import com.wordrace.model.Room;
import com.wordrace.model.Word;
import com.wordrace.result.DataResult;
import java.util.List;
import java.util.Optional;

public interface RoomService {

    //Get Operations
    DataResult<List<Room>> getAllRooms();
    DataResult<Room> getRoomById(Long id);
    DataResult<Game> getGameByRoomId(Long roomId);
    DataResult<List<Word>> getWordsByRoomId(Long roomId);

    //Post Operations
    DataResult<Room> createRoom(Room room);

    //Put Operations
    DataResult<Room> updateRoomByRoomId(Long roomId, Room room);

    //Delete Operations
    DataResult<Boolean> deleteRoomById(Long id);

}

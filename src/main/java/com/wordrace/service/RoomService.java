package com.wordrace.service;

import com.wordrace.model.Game;
import com.wordrace.model.Room;
import com.wordrace.model.Word;

import java.util.List;
import java.util.Optional;

public interface RoomService {

    //Get Operations
    Optional<List<Room>> getAllRooms();
    Optional<Room> getRoomById(Long id);
    Optional<Game> getGameByRoomId(Long roomId);
    Optional<List<Word>> getWordsByRoomId(Long roomId);

    //Post Operations
    Optional<Room> createRoom(Room room);

    //Put Operations
    Optional<Room> updateRoomByRoomId(Long roomId, Room room);

    //Delete Operations
    boolean deleteRoomById(Long id);

}

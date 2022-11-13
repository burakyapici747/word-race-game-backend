package com.wordrace.service.impl;

import com.wordrace.constant.ResultMessages;
import com.wordrace.constant.RoomMessages;
import com.wordrace.model.Game;
import com.wordrace.model.Room;
import com.wordrace.model.Word;
import com.wordrace.repository.RoomRepository;
import com.wordrace.result.DataResult;
import com.wordrace.result.Result;
import com.wordrace.result.SuccessDataResult;
import com.wordrace.result.SuccessResult;
import com.wordrace.service.RoomService;

import java.util.List;
import java.util.Optional;

public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository){
        this.roomRepository = roomRepository;
    }

    @Override
    public DataResult<List<Room>> getAllRooms() {
        final List<Room> rooms = roomRepository.findAll();

        return new SuccessDataResult<>(rooms, ResultMessages.EMPTY);
    }

    @Override
    public DataResult<Room> getRoomById(Long id) {
        final Room room = findRoomById(id);

        return new SuccessDataResult<>(room, ResultMessages.EMPTY);
    }

    @Override
    public DataResult<Game> getGameByRoomId(Long roomId) {
        final Game game = Optional.ofNullable(findRoomById(roomId).getGame())
                .orElseThrow(() -> new RuntimeException(ResultMessages.NOT_FOUND_DATA));

        return new SuccessDataResult<>(game, ResultMessages.EMPTY);
    }

    @Override
    public DataResult<List<Word>> getWordsByRoomId(Long roomId) {
        final Game game = Optional.ofNullable(findRoomById(roomId).getGame())
                .orElseThrow(() -> new RuntimeException(ResultMessages.NOT_FOUND_DATA));

        return new SuccessDataResult<>(game.getWords(), ResultMessages.EMPTY);
    }

    @Override
    public DataResult<Room> createRoom(Room room) {
        boolean isRoomNameAlreadyExist = roomRepository.findByRoomName(room.getRoomName())
                .isPresent();

        if(isRoomNameAlreadyExist){
            return new SuccessDataResult<>(null, RoomMessages.ROOM_NAME_ALREADY_EXIST);
        }

        return new SuccessDataResult<>(roomRepository.save(room), ResultMessages.SUCCESS_CREATE);
    }

    @Override
    public DataResult<Room> updateRoomByRoomId(Long roomId, Room room) {
        final Room roomToUpdate = findRoomById(roomId);

        final Optional<Room> hasSameNameRoom = roomRepository.findByRoomName(room.getRoomName());

        if(hasSameNameRoom.isPresent() && hasSameNameRoom.get().getId() != roomId){
            return new SuccessDataResult<>(null, RoomMessages.ROOM_NAME_ALREADY_EXIST);
        }

        roomToUpdate.setCreatorId(room.getCreatorId());
        roomToUpdate.setRoomName(room.getRoomName());
        roomToUpdate.setWinnerId(room.getWinnerId());
        roomToUpdate.setCapacity(room.getCapacity());

        return new SuccessDataResult<>(roomRepository.save(roomToUpdate), ResultMessages.SUCCESS_UPDATE);
    }

    @Override
    public Result deleteRoomById(Long id) {
        final Room room = findRoomById(id);

        roomRepository.delete(room);

        return new SuccessResult(ResultMessages.SUCCESS_DELETE);
    }


    private Room findRoomById(Long id){
        return roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(ResultMessages.NOT_FOUND_DATA));
    }

}

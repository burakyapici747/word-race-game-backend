package com.wordrace.service.impl;

import com.wordrace.constant.ResultMessages;
import com.wordrace.constant.RoomMessages;
import com.wordrace.exception.EntityAlreadyExistException;
import com.wordrace.exception.EntityNotFoundException;
import com.wordrace.model.Game;
import com.wordrace.model.Room;
import com.wordrace.model.Word;
import com.wordrace.repository.RoomRepository;
import com.wordrace.request.room.RoomPostRequest;
import com.wordrace.request.room.RoomPutRequest;
import com.wordrace.result.DataResult;
import com.wordrace.result.Result;
import com.wordrace.result.SuccessDataResult;
import com.wordrace.result.SuccessResult;
import com.wordrace.service.RoomService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
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
    public DataResult<Room> createRoom(RoomPostRequest roomPostRequest) {
        boolean isRoomNameAlreadyExist = roomRepository.findByRoomName(roomPostRequest.getRoomName())
                .isPresent();

        if(isRoomNameAlreadyExist){
            throw new EntityAlreadyExistException(ResultMessages.ALREADY_EXIST);
        }

        final Room room = new Room();
        room.setCreatorId(roomPostRequest.getCreatorId());
        room.setRoomName(roomPostRequest.getRoomName());
        room.setCapacity(roomPostRequest.getCapacity());

        return new SuccessDataResult<>(roomRepository.save(room), ResultMessages.SUCCESS_CREATE);
    }

    @Override
    public DataResult<Room> updateRoomById(Long id, RoomPutRequest roomPutRequest) {
        final Room roomToUpdate = findRoomById(id);
        roomToUpdate.setWinnerId(roomPutRequest.getWinnerId());
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
                .orElseThrow(() -> new EntityNotFoundException(ResultMessages.NOT_FOUND_DATA));
    }

}

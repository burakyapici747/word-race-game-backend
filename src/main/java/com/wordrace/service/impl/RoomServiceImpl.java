package com.wordrace.service.impl;

import com.wordrace.constant.ResultMessages;
import com.wordrace.dto.GameDto;
import com.wordrace.dto.RoomDto;
import com.wordrace.dto.UserDto;
import com.wordrace.dto.WordDto;
import com.wordrace.exception.EntityAlreadyExistException;
import com.wordrace.exception.EntityNotFoundException;
import com.wordrace.model.Game;
import com.wordrace.model.Room;
import com.wordrace.repository.RoomRepository;
import com.wordrace.request.room.RoomPostRequest;
import com.wordrace.request.room.RoomPutRequest;
import com.wordrace.result.DataResult;
import com.wordrace.result.Result;
import com.wordrace.result.SuccessDataResult;
import com.wordrace.result.SuccessResult;
import com.wordrace.service.RoomService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final ModelMapper modelMapper;

    public RoomServiceImpl(RoomRepository roomRepository, ModelMapper modelMapper){
        this.roomRepository = roomRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public DataResult<List<RoomDto>> getAllRooms() {
        final List<RoomDto> roomDtos = roomRepository.findAll()
                .stream()
                .map(room-> modelMapper.map(room, RoomDto.class))
                .toList();
        return new SuccessDataResult<>(roomDtos, ResultMessages.EMPTY);
    }

    @Override
    public DataResult<RoomDto> getRoomById(Long id) {
        final Room room = findRoomById(id);
        final RoomDto roomDto = modelMapper.map(room, RoomDto.class);
        return new SuccessDataResult<>(roomDto, ResultMessages.EMPTY);
    }

    @Override
    public DataResult<GameDto> getGameByRoomId(Long roomId) {
        final Game game = Optional.ofNullable(findRoomById(roomId).getGame())
                .orElseThrow(() -> new RuntimeException(ResultMessages.NOT_FOUND_DATA));
        final GameDto gameDto = modelMapper.map(game, GameDto.class);
        return new SuccessDataResult<>(gameDto, ResultMessages.EMPTY);
    }

    @Override
    public DataResult<List<WordDto>> getWordsByRoomId(Long roomId) {
        final Game game = Optional.ofNullable(findRoomById(roomId).getGame())
                .orElseThrow(() -> new RuntimeException(ResultMessages.NOT_FOUND_DATA));
        final List<WordDto> wordDtos = game.getWords()
                .stream()
                .map(word-> modelMapper.map(word, WordDto.class))
                .toList();
        return new SuccessDataResult<>(wordDtos, ResultMessages.EMPTY);
    }

    @Override
    public DataResult<List<UserDto>> getUsersByRoomId(Long roomId) {
        final Room room = findRoomById(roomId);
        final List<UserDto> userDtos = room.getUsers()
                .stream().map(user -> modelMapper.map(user, UserDto.class))
                .toList();

        return new SuccessDataResult<>(userDtos, ResultMessages.EMPTY);
    }

    @Override
    public DataResult<RoomDto> createRoom(RoomPostRequest roomPostRequest) {
        boolean isRoomNameAlreadyExist = roomRepository.findByRoomName(roomPostRequest.getRoomName())
                .isPresent();

        if(isRoomNameAlreadyExist){
            throw new EntityAlreadyExistException(ResultMessages.ALREADY_EXIST);
        }

        final Room room = new Room();
        room.setCreatorId(roomPostRequest.getCreatorId());
        room.setRoomName(roomPostRequest.getRoomName());
        room.setCapacity(roomPostRequest.getCapacity());
        final RoomDto roomDto = modelMapper.map(roomRepository.save(room), RoomDto.class);
        return new SuccessDataResult<>(roomDto, ResultMessages.SUCCESS_CREATE);
    }

    @Override
    public DataResult<RoomDto> updateRoomById(Long id, RoomPutRequest roomPutRequest) {
        final Room roomToUpdate = findRoomById(id);
        roomToUpdate.setWinnerId(roomPutRequest.getWinnerId());
        final RoomDto roomDto = modelMapper.map(roomRepository.save(roomToUpdate), RoomDto.class);
        return new SuccessDataResult<>(roomDto, ResultMessages.SUCCESS_UPDATE);
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

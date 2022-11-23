package com.wordrace.service.impl;

import com.wordrace.constant.ResultMessages;
import com.wordrace.constant.RoomMessages;
import com.wordrace.dto.GameDto;
import com.wordrace.dto.RoomDto;
import com.wordrace.dto.UserDto;
import com.wordrace.dto.WordDto;
import com.wordrace.exception.EntityNotFoundException;
import com.wordrace.model.Game;
import com.wordrace.model.Room;
import com.wordrace.repository.RoomRepository;
import com.wordrace.request.room.RoomPostRequest;
import com.wordrace.request.room.RoomPutRequest;
import com.wordrace.result.*;
import com.wordrace.service.RoomService;
import com.wordrace.util.GlobalHelper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

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
        final List<RoomDto> roomDtos = GlobalHelper.listDtoConverter(modelMapper,
                roomRepository.findAll(), RoomDto.class);

        return new SuccessDataResult<>(roomDtos, ResultMessages.EMPTY);
    }

    @Override
    public DataResult<RoomDto> getRoomById(final UUID id) {
        final Room room = findRoomById(id);
        final RoomDto roomDto = modelMapper.map(room, RoomDto.class);

        return new SuccessDataResult<>(roomDto, ResultMessages.EMPTY);
    }

    @Override
    public DataResult<GameDto> getGameByRoomId(final UUID roomId) {
        final Game game = findRoomById(roomId).getGame();
        final GameDto gameDto = modelMapper.map(game, GameDto.class);

        return new SuccessDataResult<>(gameDto, ResultMessages.EMPTY);
    }

    @Override
    public DataResult<List<WordDto>> getWordsByRoomId(final UUID roomId) {
        final Game game = findRoomById(roomId).getGame();
        final List<WordDto> wordDtos = GlobalHelper.listDtoConverter(modelMapper, game.getWords(), WordDto.class);

        return new SuccessDataResult<>(wordDtos, ResultMessages.EMPTY);
    }

    @Override
    public DataResult<List<UserDto>> getUsersByRoomId(final UUID roomId) {
        final Room room = findRoomById(roomId);
        final List<UserDto> userDtos = GlobalHelper.listDtoConverter(modelMapper, room.getUsers(), UserDto.class);

        return new SuccessDataResult<>(userDtos, ResultMessages.EMPTY);
    }

    @Override
    public DataResult<RoomDto> createRoom(final RoomPostRequest roomPostRequest) {
        GlobalHelper.checkIfAlreadyExist(roomRepository.findByRoomName(roomPostRequest.getRoomName()));

        final Room room = new Room();

        room.setCreatorId(UUID.fromString(roomPostRequest.getCreatorId()));
        room.setRoomName(roomPostRequest.getRoomName());
        room.setCapacity(roomPostRequest.getCapacity());

        final RoomDto roomDto = modelMapper.map(roomRepository.save(room), RoomDto.class);

        return new SuccessDataResult<>(roomDto, ResultMessages.SUCCESS_CREATE);
    }

    @Override
    public DataResult<RoomDto> updateRoomById(final UUID id, final RoomPutRequest roomPutRequest) {
        final Room roomToUpdate = findRoomById(id);

        boolean isUserInRoom = roomToUpdate.getUsers()
                        .stream()
                        .anyMatch(user -> user.getId().equals(UUID.fromString(roomPutRequest.getWinnerId())));

        if(!isUserInRoom)
            return new ErrorDataResult<>(null, RoomMessages.ROOM_USER_NOT_JOINED);

        roomToUpdate.setWinnerId(UUID.fromString(roomPutRequest.getWinnerId()));

        final RoomDto roomDto = modelMapper.map(roomRepository.save(roomToUpdate), RoomDto.class);

        return new SuccessDataResult<>(roomDto, ResultMessages.SUCCESS_UPDATE);
    }

    @Override
    public Result deleteRoomById(final UUID id) {
        final Room room = findRoomById(id);

        roomRepository.delete(room);

        return new SuccessResult(ResultMessages.SUCCESS_DELETE);
    }

    private Room findRoomById(final UUID id){
        return roomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ResultMessages.NOT_FOUND_DATA));
    }
}

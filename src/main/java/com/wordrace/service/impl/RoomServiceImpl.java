package com.wordrace.service.impl;

import com.wordrace.api.response.*;
import com.wordrace.constant.ResponseConstant;
import com.wordrace.constant.RoomConstant;
import com.wordrace.dto.GameDto;
import com.wordrace.dto.RoomDto;
import com.wordrace.dto.UserDto;
import com.wordrace.dto.WordDto;
import com.wordrace.exception.EntityNotFoundException;
import com.wordrace.model.Game;
import com.wordrace.model.Room;
import com.wordrace.repository.RoomRepository;
import com.wordrace.api.request.room.RoomPostRequest;
import com.wordrace.api.request.room.RoomPutRequest;
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
    public DataResponse<List<RoomDto>> getAllRooms() {
        final List<RoomDto> roomDtos = GlobalHelper.listDtoConverter(modelMapper,
                roomRepository.findAll(), RoomDto.class);

        return new SuccessDataResponse<>(roomDtos, ResponseConstant.EMPTY);
    }

    @Override
    public DataResponse<RoomDto> getRoomById(final UUID id) {
        final Room room = findRoomById(id);
        final RoomDto roomDto = modelMapper.map(room, RoomDto.class);

        return new SuccessDataResponse<>(roomDto, ResponseConstant.EMPTY);
    }

    @Override
    public DataResponse<GameDto> getGameByRoomId(final UUID roomId) {
        final Game game = findRoomById(roomId).getGame();
        final GameDto gameDto = modelMapper.map(game, GameDto.class);

        return new SuccessDataResponse<>(gameDto, ResponseConstant.EMPTY);
    }

    @Override
    public DataResponse<List<WordDto>> getWordsByRoomId(final UUID roomId) {
        final Game game = findRoomById(roomId).getGame();
        final List<WordDto> wordDtoList = GlobalHelper.listDtoConverter(modelMapper, game.getWords(), WordDto.class);

        return new SuccessDataResponse<>(wordDtoList, ResponseConstant.EMPTY);
    }

    @Override
    public DataResponse<List<UserDto>> getUsersByRoomId(final UUID roomId) {
        final Room room = findRoomById(roomId);
        final List<UserDto> userDtoList = GlobalHelper.listDtoConverter(modelMapper, room.getUsers(), UserDto.class);

        return new SuccessDataResponse<>(userDtoList, ResponseConstant.EMPTY);
    }

    @Override
    public DataResponse<RoomDto> createRoom(final RoomPostRequest roomPostRequest) {
        GlobalHelper.checkIfAlreadyExist(roomRepository.findByRoomName(roomPostRequest.getRoomName())
                .orElse(null));

        final Room room = new Room();

        room.setCreatorId(UUID.fromString(roomPostRequest.getCreatorId()));
        room.setRoomName(roomPostRequest.getRoomName());
        room.setCapacity(roomPostRequest.getCapacity());

        final RoomDto roomDto = modelMapper.map(roomRepository.save(room), RoomDto.class);

        return new SuccessDataResponse<>(roomDto, ResponseConstant.SUCCESS_CREATE);
    }

    @Override
    public DataResponse<RoomDto> updateRoomById(final UUID id, final RoomPutRequest roomPutRequest) {
        final Room roomToUpdate = findRoomById(id);

        boolean isUserInRoom = roomToUpdate.getUsers()
                        .stream()
                        .anyMatch(user -> user.getId().equals(UUID.fromString(roomPutRequest.getWinnerId())));

        if(!isUserInRoom)
            return new ErrorDataResponse<>(null, RoomConstant.ROOM_USER_NOT_JOINED);

        roomToUpdate.setWinnerId(UUID.fromString(roomPutRequest.getWinnerId()));

        final RoomDto roomDto = modelMapper.map(roomRepository.save(roomToUpdate), RoomDto.class);

        return new SuccessDataResponse<>(roomDto, ResponseConstant.SUCCESS_UPDATE);
    }

    @Override
    public BaseResponse deleteRoomById(final UUID id) {
        final Room room = findRoomById(id);

        roomRepository.delete(room);

        return new SuccessResponse(ResponseConstant.SUCCESS_DELETE);
    }

    protected Room findRoomById(final UUID id){
        return roomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ResponseConstant.NOT_FOUND_DATA));
    }
}

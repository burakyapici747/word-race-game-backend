package com.wordrace.service.impl;

import com.wordrace.constant.ResultMessages;
import com.wordrace.constant.RoomMessages;
import com.wordrace.constant.UserMessages;
import com.wordrace.dto.*;
import com.wordrace.exception.EntityAlreadyExistException;
import com.wordrace.exception.EntityNotFoundException;
import com.wordrace.model.Room;
import com.wordrace.model.User;
import com.wordrace.model.UserScore;
import com.wordrace.repository.RoomRepository;
import com.wordrace.repository.UserRepository;
import com.wordrace.request.user.UserPostJoinRoomRequest;
import com.wordrace.request.user.UserPostRequest;
import com.wordrace.request.user.UserPostScoreRequest;
import com.wordrace.request.user.UserPutRequest;
import com.wordrace.result.*;
import com.wordrace.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, RoomRepository roomRepository, ModelMapper modelMapper){
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public DataResult<List<UserDto>> getAllUsers() {
        List<UserDto> userDtos = userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(userDtos, "");
    }

    @Override
    public DataResult<UserDto> getUserById(UUID id) {
        final User user = findUserById(id);

        return new SuccessDataResult<>(modelMapper.map(user, UserDto.class), ResultMessages.EMPTY);
    }

    @Override
    public DataResult<List<GameDto>> getAllGamesByUserId(UUID userId) {
        final User user = findUserById(userId);

        List<GameDto> userGames = user.getRooms()
                .stream()
                .map(Room::getGame)
                .map(game -> modelMapper.map(game, GameDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(userGames, ResultMessages.EMPTY);
    }

    @Override
    public DataResult<List<RoomDto>> getAllRoomsByUserId(UUID userId) {
        final User user = findUserById(userId);
        List<RoomDto> roomDtos = user.getRooms()
                .stream().map(room -> modelMapper.map(room, RoomDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(roomDtos, ResultMessages.EMPTY);
    }

    @Override
    public DataResult<UserDto> createUser(UserPostRequest userPostRequest) {
        boolean isEmailExists = userRepository.findUserByEmail(userPostRequest.getEmail())
                .isPresent();

        if(isEmailExists){
            throw new EntityAlreadyExistException(ResultMessages.ALREADY_EXIST);
        }

        final User user = new User();

        user.setEmail(userPostRequest.getEmail());
        user.setPassword(userPostRequest.getPassword());

        return new SuccessDataResult<>(modelMapper.map(userRepository.save(user), UserDto.class), ResultMessages.SUCCESS_CREATE);
    }

    @Override
    public DataResult<RoomDto> joinRoom(UserPostJoinRoomRequest userPostJoinRoomRequest) {
        final User user = findUserById(UUID.fromString(userPostJoinRoomRequest.getUserId()));
        final Room room = findRoomById(UUID.fromString(userPostJoinRoomRequest.getRoomId()));

        boolean isAlreadyUserInRoom = room.getUsers().stream()
                .anyMatch(inRoomUser -> inRoomUser.getId().equals(user.getId()));

        if(isAlreadyUserInRoom){
            throw new EntityAlreadyExistException(RoomMessages.ROOM_USER_ALREADY_IN);
        }

        if(room.getUsers().size() + 1 > room.getCapacity()){
            return new ErrorDataResult<>(null, RoomMessages.ROOM_CAPACITY_IS_FULL);
        }

        user.getRooms().add(room);

        return new SuccessDataResult<>(modelMapper.map(userRepository.save(user), RoomDto.class), UserMessages.ROOM_JOIN_SUCCESSFULLY);
    }

    @Override
    public DataResult<RoomDto> addScoreToUser(UserPostScoreRequest userPostScoreRequest) {
        final User user = findUserById(UUID.fromString(userPostScoreRequest.getUserId()));
        final List<Room> filteredRooms = user.getRooms()
                .stream()
                .filter(room -> room.getGame().getId().equals(userPostScoreRequest.getGameId()))
                .collect(Collectors.toList());

        if(filteredRooms.size() == 0){
            throw new EntityNotFoundException(ResultMessages.NOT_FOUND_DATA);
        }

        final Room room = filteredRooms.get(0);
        final UserScore newScore = new UserScore();

        newScore.setUser(user);
        newScore.setGame(room.getGame());
        newScore.setScore(userPostScoreRequest.getScore());
        user.getUserScore().add(newScore);

        userRepository.save(user);

        return new SuccessDataResult<>(modelMapper.map(room, RoomDto.class), ResultMessages.SUCCESS_CREATE);
    }

    @Override
    public DataResult<UserDto> updateUser(UUID id, UserPutRequest userPutRequest) {
        final User userToUpdate = findUserById(id);

        if(userToUpdate.getNickName() == null || (userToUpdate.getNickName() != null && !userToUpdate.getNickName().equals(userPutRequest.getNickName()))){

            final Optional<User> hasSameNickNameUser = userRepository.findUserByNickName(userPutRequest.getNickName());

            if(hasSameNickNameUser.isPresent() && !hasSameNickNameUser.get().getId().equals(id)){
                throw new EntityAlreadyExistException(ResultMessages.ALREADY_EXIST);
            }

            userToUpdate.setNickName(userPutRequest.getNickName());
            userRepository.save(userToUpdate);
        }
        return new SuccessDataResult<>(modelMapper.map(userToUpdate, UserDto.class), ResultMessages.SUCCESS_UPDATE);
    }

    @Override
    public Result deleteUserById(UUID id) {
        final User user = findUserById(id);

        userRepository.delete(user);

        return new SuccessResult(ResultMessages.SUCCESS_DELETE);
    }

    private User findUserById(UUID id){
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ResultMessages.NOT_FOUND_DATA));
    }

    private Room findRoomById(UUID id){
        return roomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ResultMessages.NOT_FOUND_DATA));
    }
}

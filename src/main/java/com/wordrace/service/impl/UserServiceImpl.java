package com.wordrace.service.impl;

import com.wordrace.constant.ResultMessages;
import com.wordrace.constant.RoomMessages;
import com.wordrace.constant.UserMessages;
import com.wordrace.dto.*;
import com.wordrace.exception.EntityAlreadyExistException;
import com.wordrace.exception.EntityNotFoundException;
import com.wordrace.model.Game;
import com.wordrace.model.Room;
import com.wordrace.model.User;
import com.wordrace.model.UserScore;
import com.wordrace.repository.RoomRepository;
import com.wordrace.repository.UserRepository;
import com.wordrace.request.user.UserPostJoinRoomRequest;
import com.wordrace.request.user.UserPostRequest;
import com.wordrace.request.user.UserPostScoreRequest;
import com.wordrace.request.user.UserPutRequest;
import com.wordrace.result.DataResult;
import com.wordrace.result.Result;
import com.wordrace.result.SuccessDataResult;
import com.wordrace.result.SuccessResult;
import com.wordrace.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//TODO: ISLEMLERIMDE EXCEPTION HANDLING YOK CUNKU NASIL YAPMAM GEREKTIGI HAKKINDA DUSUNUYORUM!!!

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    private ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, RoomRepository roomRepository){
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public DataResult<List<UserDto>> getAllUsers() {
        List<UserDto> userDtos = userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .toList();

        return new SuccessDataResult<>(userDtos, "");
    }

    @Override
    public DataResult<UserDto> getUserById(Long id) {
        final User user = findUserById(id);

        return new SuccessDataResult<>(modelMapper.map(user, UserDto.class), ResultMessages.EMPTY);
    }

    @Override
    public DataResult<List<GameDto>> getAllGamesByUserId(Long userId) {
        final User user = findUserById(userId);

        List<GameDto> userGames = user.getRooms()
                .stream()
                .map(Room::getGame)
                .map(game -> modelMapper.map(game, GameDto.class))
                .toList();

        return new SuccessDataResult<>(userGames, ResultMessages.EMPTY);
    }

    @Override
    public DataResult<List<RoomDto>> getAllRoomsByUserId(Long userId) {
        final User user = findUserById(userId);
        List<RoomDto> roomDtos = user.getRooms()
                .stream().map(room -> modelMapper.map(room, RoomDto.class))
                .toList();

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
        final User user = findUserById(userPostJoinRoomRequest.getUserId());
        final Room room = findRoomById(userPostJoinRoomRequest.getRoomId());

        //TODO: "ROOM_CAPACITY_IS_FULL" için belki exception olusturulabilir. Bunun için düşün...
        if(room.getUsers().size() + 1 > room.getCapacity()){
            return new SuccessDataResult<>(null, RoomMessages.ROOM_CAPACITY_IS_FULL);
        }
        room.getUsers().add(user);

        return new SuccessDataResult<>(modelMapper.map(roomRepository.save(room), RoomDto.class), UserMessages.ROOM_JOIN_SUCCESSFULLY);
    }

    @Override
    public DataResult<RoomDto> addScoreToUser(UserPostScoreRequest userPostScoreRequest) {
        final User user = findUserById(userPostScoreRequest.getUserId());

        final List<Room> filteredRooms = user.getRooms()
                .stream()
                .filter(room -> room.getGame().getId().equals(userPostScoreRequest.getGameId()))
                .toList();

        if(filteredRooms.size() == 0){
            throw new EntityNotFoundException(ResultMessages.NOT_FOUND_DATA);
        }

        final Room room = filteredRooms.get(0);

        UserScore newScore = new UserScore();
        newScore.setUser(user);
        newScore.setGame(room.getGame());
        newScore.setScore(userPostScoreRequest.getScore());

        user.getUserScore().add(newScore);

        userRepository.save(user);

        return new SuccessDataResult<>(modelMapper.map(room, RoomDto.class), ResultMessages.SUCCESS_CREATE);
    }

    @Override
    public DataResult<UserDto> updateUser(Long id, UserPutRequest userPutRequest) {
        final User userToUpdate = findUserById(id);

        if(!userToUpdate.getNickName().equals(userPutRequest.getNickName())){

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
    public Result deleteUserById(Long id) {
        final User user = findUserById(id);

        userRepository.delete(user);

        return new SuccessResult(ResultMessages.SUCCESS_DELETE);
    }

    private User findUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ResultMessages.NOT_FOUND_DATA));
    }

    private Room findRoomById(Long id){
        return roomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ResultMessages.NOT_FOUND_DATA));
    }
}

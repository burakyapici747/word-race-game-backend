package com.wordrace.service.impl;

import com.wordrace.constant.ResultMessages;
import com.wordrace.constant.RoomMessages;
import com.wordrace.constant.UserMessages;
import com.wordrace.dto.*;
import com.wordrace.exception.EntityAlreadyExistException;
import com.wordrace.exception.EntityNotFoundException;
import com.wordrace.exception.RoomCapacityIsFullException;
import com.wordrace.model.Game;
import com.wordrace.model.Room;
import com.wordrace.model.User;
import com.wordrace.model.UserScore;
import com.wordrace.repository.UserRepository;
import com.wordrace.request.user.UserPostJoinRoomRequest;
import com.wordrace.request.user.UserPostRequest;
import com.wordrace.request.user.UserPostScoreRequest;
import com.wordrace.request.user.UserPutRequest;
import com.wordrace.result.*;
import com.wordrace.service.UserService;
import com.wordrace.util.GlobalHelper;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final RoomServiceImpl roomService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public UserServiceImpl(RoomServiceImpl roomService, UserRepository userRepository,
                           ModelMapper modelMapper,
                           PasswordEncoder passwordEncoder){
        this.roomService = roomService;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public DataResult<List<UserDto>> getAllUsers() {
        final List<UserDto> userDtos = GlobalHelper.listDtoConverter(modelMapper,
                userRepository.findAll(), UserDto.class);

        return new SuccessDataResult<>(userDtos, "");
    }

    @Override
    public DataResult<UserDto> getUserById(final UUID id) {
        final User user = findUserById(id);

        return new SuccessDataResult<>(modelMapper.map(user, UserDto.class), ResultMessages.EMPTY);
    }

    @Override
    public DataResult<List<GameDto>> getAllGamesByUserId(final UUID userId) {
        final User user = findUserById(userId);
        final List<Game> userGames = user.getRooms()
                .stream().
                map(Room::getGame).collect(Collectors.toList());
        final List<GameDto> userGameDtos = GlobalHelper.listDtoConverter(modelMapper,
                userGames, GameDto.class);

        return new SuccessDataResult<>(userGameDtos, ResultMessages.EMPTY);
    }

    @Override
    public DataResult<List<RoomDto>> getAllRoomsByUserId(final UUID userId) {
        final User user = findUserById(userId);
        final List<RoomDto> roomDtos = GlobalHelper.listDtoConverter(modelMapper, user.getRooms(), RoomDto.class);

        return new SuccessDataResult<>(roomDtos, ResultMessages.EMPTY);
    }

    @Override
    public DataResult<UserDto> createUser(final UserPostRequest userPostRequest) {
        GlobalHelper.checkIfAlreadyExist(userRepository.findUserByEmail(userPostRequest.getEmail()).orElse(null));

        final User user = new User();

        user.setEmail(userPostRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userPostRequest.getPassword()));

        return new SuccessDataResult<>(modelMapper.map(userRepository.save(user), UserDto.class), ResultMessages.SUCCESS_CREATE);
    }

    @Override
    public DataResult<RoomDto> joinRoom(final UserPostJoinRoomRequest userPostJoinRoomRequest) {
        final User user = findUserById(UUID.fromString(userPostJoinRoomRequest.getUserId()));
        final Room room = roomService.findRoomById(UUID.fromString(userPostJoinRoomRequest.getRoomId()));

        isAlreadyUserInRoom(room.getUsers(), user);
        checkRoomCapacity(room.getUsers().size(), room.getCapacity());
        user.getRooms().add(room);
        userRepository.save(user);

        return new SuccessDataResult<>(modelMapper.map(room, RoomDto.class), UserMessages.ROOM_JOIN_SUCCESSFULLY);
    }

    @Override
    public DataResult<RoomDto> addScoreToUser(final UserPostScoreRequest userPostScoreRequest) {
        final User user = findUserById(UUID.fromString(userPostScoreRequest.getUserId()));
        final Optional<Room> optionalRoom = user.getRooms()
                .stream()
                .filter(room -> room.getGame().getId().equals(UUID.fromString(userPostScoreRequest.getGameId())))
                .findFirst();

        GlobalHelper.checkIfNull(optionalRoom);

        final UserScore newScore = new UserScore();

        newScore.setUser(user);
        newScore.setGame(optionalRoom.get().getGame());
        newScore.setScore(userPostScoreRequest.getScore());
        user.getUserScore().add(newScore);

        userRepository.save(user);

        return new SuccessDataResult<>(modelMapper.map(optionalRoom.get(), RoomDto.class), ResultMessages.SUCCESS_CREATE);
    }

    @Override
    public DataResult<UserDto> updateUser(final UUID id, final UserPutRequest userPutRequest) {
        final User userToUpdate = findUserById(id);

        if(nickNameIsValid(userToUpdate, userPutRequest.getNickName())){
            checkIfNickNameAlreadyExist(userToUpdate.getId(), userRepository.findUserByNickName(userPutRequest.getNickName()));

            userToUpdate.setNickName(userPutRequest.getNickName());
            userRepository.save(userToUpdate);
        }

        return new SuccessDataResult<>(modelMapper.map(userToUpdate, UserDto.class), ResultMessages.SUCCESS_UPDATE);
    }

    @Override
    public Result deleteUserById(final UUID id) {
        final User user = findUserById(id);

        userRepository.delete(user);

        return new SuccessResult(ResultMessages.SUCCESS_DELETE);
    }

    protected User findUserById(final UUID id){
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ResultMessages.NOT_FOUND_DATA));
    }

    private void isAlreadyUserInRoom(final List<User> users, final User user){
        if(users.stream().anyMatch(inRoomUser -> inRoomUser.getId().equals(user.getId()))){
            throw new EntityAlreadyExistException(RoomMessages.ROOM_USER_ALREADY_IN);
        }
    }

    private void checkRoomCapacity(final int usersCount, int roomCapacity){
        if(usersCount + 1 > roomCapacity){
            throw new RoomCapacityIsFullException(RoomMessages.ROOM_CAPACITY_IS_FULL);
        }
    }

    private boolean nickNameIsValid(final User userToUpdate, final String userPutRequestNickName){
        return userToUpdate.getNickName() != null && !userToUpdate.getNickName().equals(userPutRequestNickName);
    }

    private void checkIfNickNameAlreadyExist(final UUID userId, final Optional<User> resultFindUserByNickName){
        if(resultFindUserByNickName.isPresent() && !resultFindUserByNickName.get().getId().equals(userId)){
            throw new EntityAlreadyExistException(ResultMessages.ALREADY_EXIST);
        }
    }
}

package com.wordrace.service.impl;

import com.wordrace.constant.ResultMessages;
import com.wordrace.constant.RoomMessages;
import com.wordrace.constant.UserMessages;
import com.wordrace.exception.EntityAlreadyExistException;
import com.wordrace.exception.EntityNotFoundException;
import com.wordrace.model.Game;
import com.wordrace.model.Room;
import com.wordrace.model.User;
import com.wordrace.model.UserScore;
import com.wordrace.repository.RoomRepository;
import com.wordrace.repository.UserRepository;
import com.wordrace.repository.UserScoreRepository;
import com.wordrace.request.user.UserPostJoinRoomRequest;
import com.wordrace.request.user.UserPostRequest;
import com.wordrace.request.user.UserPostScoreRequest;
import com.wordrace.request.user.UserPutRequest;
import com.wordrace.result.DataResult;
import com.wordrace.result.Result;
import com.wordrace.result.SuccessDataResult;
import com.wordrace.result.SuccessResult;
import com.wordrace.service.UserService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

//TODO: ISLEMLERIMDE EXCEPTION HANDLING YOK CUNKU NASIL YAPMAM GEREKTIGI HAKKINDA DUSUNUYORUM!!!

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    public UserServiceImpl(UserRepository userRepository, RoomRepository roomRepository){
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public DataResult<List<User>> getAllUsers() {
        final List<User> users = userRepository.findAll();

        return new SuccessDataResult<>(users, "");
    }

    @Override
    public DataResult<User> getUserById(Long id) {
        final User user = findUserById(id);

        return new SuccessDataResult<>(user, ResultMessages.EMPTY);
    }

    @Override
    public DataResult<List<Game>> getAllGamesByUserId(Long userId) {
        final User user = findUserById(userId);

        List<Game> userGames = user.getRooms()
                .stream()
                .map(Room::getGame)
                .toList();

        return new SuccessDataResult<>(userGames, ResultMessages.EMPTY);
    }

    @Override
    public DataResult<List<Room>> getAllRoomsByUserId(Long userId) {
        final User user = findUserById(userId);

        return new SuccessDataResult<>(user.getRooms(), ResultMessages.EMPTY);
    }

    @Override
    public DataResult<User> createUser(UserPostRequest userPostRequest) {
        boolean isEmailExists = userRepository.findUserByEmail(userPostRequest.getEmail())
                .isPresent();

        if(isEmailExists){
            throw new EntityAlreadyExistException(ResultMessages.ALREADY_EXIST);
        }

        final User user = new User();
        user.setEmail(userPostRequest.getEmail());
        user.setPassword(userPostRequest.getPassword());

        return new SuccessDataResult<>(userRepository.save(user), ResultMessages.SUCCESS_CREATE);
    }

    @Override
    public DataResult<Room> joinRoom(UserPostJoinRoomRequest userPostJoinRoomRequest) {
        final User user = findUserById(userPostJoinRoomRequest.getUserId());
        final Room room = findRoomById(userPostJoinRoomRequest.getRoomId());

        //TODO: "ROOM_CAPACITY_IS_FULL" için belki exception olusturulabilir. Bunun için düşün...
        if(room.getUsers().size() + 1 > room.getCapacity()){
            return new SuccessDataResult<>(null, RoomMessages.ROOM_CAPACITY_IS_FULL);
        }

        room.getUsers().add(user);
        roomRepository.save(room);
        return new SuccessDataResult<>(room, UserMessages.ROOM_JOIN_SUCCESSFULLY);
    }

    @Override
    public DataResult<Room> addScoreToUser(UserPostScoreRequest userPostScoreRequest) {
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

        return new SuccessDataResult<>(room, ResultMessages.SUCCESS_CREATE);
    }

    @Override
    public DataResult<User> updateUser(Long id, UserPutRequest userPutRequest) {
        final User userToUpdate = findUserById(id);

        if(!userToUpdate.getNickName().equals(userPutRequest.getNickName())){

            final Optional<User> hasSameNickNameUser = userRepository.findUserByNickName(userPutRequest.getNickName());

            if(hasSameNickNameUser.isPresent() && !hasSameNickNameUser.get().getId().equals(id)){
                throw new EntityAlreadyExistException(ResultMessages.ALREADY_EXIST);
            }

            userToUpdate.setNickName(userPutRequest.getNickName());
            userRepository.save(userToUpdate);
        }
        return new SuccessDataResult<>(userToUpdate, ResultMessages.SUCCESS_UPDATE);
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

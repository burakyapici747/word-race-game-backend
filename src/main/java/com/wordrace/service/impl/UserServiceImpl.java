package com.wordrace.service.impl;

import com.wordrace.constant.ResultMessages;
import com.wordrace.constant.RoomMessages;
import com.wordrace.constant.UserMessages;
import com.wordrace.model.Game;
import com.wordrace.model.Room;
import com.wordrace.model.User;
import com.wordrace.model.UserScore;
import com.wordrace.repository.RoomRepository;
import com.wordrace.repository.UserRepository;
import com.wordrace.repository.UserScoreRepository;
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

        List<Game> userGames = user.getRooms().stream()
                .map(room -> room.getGame())
                .toList();

        return new SuccessDataResult<>(userGames, ResultMessages.EMPTY);
    }

    @Override
    public DataResult<List<Room>> getAllRoomsByUserId(Long userId) {
        final User user = findUserById(userId);

        return new SuccessDataResult<>(user.getRooms(), ResultMessages.EMPTY);
    }

    @Override
    public DataResult<User> createUser(final User user) {
        boolean isEmailExists = userRepository.findUserByEmail(user.getEmail()).isPresent();

        if(isEmailExists){
            return new SuccessDataResult<>(null, ResultMessages.ALREADY_EXIST);
        }

        return new SuccessDataResult<>(userRepository.save(user), ResultMessages.SUCCESS_CREATE);
    }

    @Override
    public DataResult<Room> joinRoom(Long userId, Long roomId) {
        final User user = findUserById(userId);
        final Room room = roomRepository.findById(roomId).orElseThrow(() -> new RuntimeException(ResultMessages.NOT_FOUND_DATA));

        if(room.getUsers().size() + 1 > room.getCapacity()){
            return new SuccessDataResult<>(null, RoomMessages.ROOM_CAPACITY_IS_FULL);
        }

        room.getUsers().add(user);
        roomRepository.save(room);

        return new SuccessDataResult<>(room, UserMessages.ROOM_JOIN_SUCCESSFULLY);
    }

    @Override
    public DataResult<Room> addScoreToUser(Long userId, Long gameId, int score) {
        final User user = findUserById(userId);

        final List<Room> filteredRooms = user.getRooms().stream()
                .filter(room -> room.getGame().getId() == gameId)
                .toList();

        if(filteredRooms.size() == 0){
            return new SuccessDataResult<>(null, ResultMessages.NOT_FOUND_DATA);
        }
        final Room room = filteredRooms.get(0);

        UserScore newScore = new UserScore();
        newScore.setUser(user);
        newScore.setGame(room.getGame());
        newScore.setScore(score);
        user.getUserScore().add(newScore);

        userRepository.save(user);

        return new SuccessDataResult<>(room, ResultMessages.SUCCESS_CREATE);
    }

    @Override
    public DataResult<User> updateUser(Long id, User user) {
        final User userToUpdate = findUserById(id);

        if(!userToUpdate.getNickname().equals(user.getNickname())){

            final Optional<User> hasSameNickNameUser = userRepository.findUserByNickname(user.getNickname());

            if(hasSameNickNameUser.isPresent() && hasSameNickNameUser.get().getId() != id){
                return new SuccessDataResult<>(null, ResultMessages.ALREADY_EXIST);
            }

            userToUpdate.setNickname(user.getNickname());
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
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException(ResultMessages.NOT_FOUND_DATA));
    }
}

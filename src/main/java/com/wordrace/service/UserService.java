package com.wordrace.service;

import com.wordrace.model.Game;
import com.wordrace.model.Room;
import com.wordrace.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {


    //GET OPERATIONS
    Optional<List<User>> getAllUsers();

    Optional<User> getUserById(Long id);

    Optional<List<Game>> getAllGamesByUserId(Long userId);

    Optional<List<Room>> getAllRoomsByUserId(Long userId);


    //POST OPERATIONS

    Optional<User> createUser(User user);

    Optional<Room> joinRoom(Long userId, Long roomId);

    Optional<Room> addScoreToUser(Long userId, Long gameId, int score);

    //PUT OPERATIONS

    Optional<User> updateUser(User user);

    //DELETE OPERATIONS

    boolean deleteUserById(Long id);

}
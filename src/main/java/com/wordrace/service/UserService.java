package com.wordrace.service;

import com.wordrace.model.Game;
import com.wordrace.model.Room;
import com.wordrace.model.User;
import com.wordrace.result.DataResult;
import com.wordrace.result.Result;

import java.util.List;
import java.util.Optional;

public interface UserService {

    //GET OPERATIONS
    DataResult<List<User>> getAllUsers();

    DataResult<User> getUserById(Long id);

    DataResult<List<Game>> getAllGamesByUserId(Long userId);

    DataResult<List<Room>> getAllRoomsByUserId(Long userId);


    //POST OPERATIONS

    DataResult<User> createUser(final User user);

    DataResult<Room> joinRoom(Long userId, Long roomId);

    DataResult<Room> addScoreToUser(Long userId, Long gameId, int score);

    //PUT OPERATIONS

    DataResult<User> updateUser(Long id, User user);

    //DELETE OPERATIONS

    Result deleteUserById(Long id);

}
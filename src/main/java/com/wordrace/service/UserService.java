package com.wordrace.service;

import com.wordrace.model.Game;
import com.wordrace.model.Room;
import com.wordrace.model.User;
import com.wordrace.request.user.UserPostJoinRoomRequest;
import com.wordrace.request.user.UserPostRequest;
import com.wordrace.request.user.UserPostScoreRequest;
import com.wordrace.request.user.UserPutRequest;
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

    DataResult<User> createUser(UserPostRequest userPostRequest);

    DataResult<Room> joinRoom(UserPostJoinRoomRequest userPostJoinRoomRequest);

    DataResult<Room> addScoreToUser(UserPostScoreRequest userPostScoreRequest);

    //PUT OPERATIONS

    DataResult<User> updateUser(Long id, UserPutRequest userPutRequest);

    //DELETE OPERATIONS

    Result deleteUserById(Long id);

}
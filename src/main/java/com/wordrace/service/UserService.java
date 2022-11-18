package com.wordrace.service;

import com.wordrace.dto.*;
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
    DataResult<List<UserDto>> getAllUsers();

    DataResult<UserDto> getUserById(Long id);

    DataResult<List<GameDto>> getAllGamesByUserId(Long userId);

    DataResult<List<RoomDto>> getAllRoomsByUserId(Long userId);


    //POST OPERATIONS

    DataResult<UserDto> createUser(UserPostRequest userPostRequest);

    DataResult<RoomDto> joinRoom(UserPostJoinRoomRequest userPostJoinRoomRequest);

    DataResult<RoomDto> addScoreToUser(UserPostScoreRequest userPostScoreRequest);

    //PUT OPERATIONS

    DataResult<UserDto> updateUser(Long id, UserPutRequest userPutRequest);

    //DELETE OPERATIONS

    Result deleteUserById(Long id);

}
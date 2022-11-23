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
import java.util.UUID;

public interface UserService {
    DataResult<List<UserDto>> getAllUsers();
    DataResult<UserDto> getUserById(final UUID id);
    DataResult<List<GameDto>> getAllGamesByUserId(final UUID userId);
    DataResult<List<RoomDto>> getAllRoomsByUserId(final UUID userId);
    DataResult<UserDto> createUser(final UserPostRequest userPostRequest);
    DataResult<RoomDto> joinRoom(final UserPostJoinRoomRequest userPostJoinRoomRequest);
    DataResult<RoomDto> addScoreToUser(final UserPostScoreRequest userPostScoreRequest);
    DataResult<UserDto> updateUser(final UUID id, final UserPutRequest userPutRequest);
    Result deleteUserById(final UUID id);
}
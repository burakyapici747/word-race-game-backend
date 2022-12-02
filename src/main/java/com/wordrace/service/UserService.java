package com.wordrace.service;

import com.wordrace.dto.*;
import com.wordrace.api.request.user.UserPostJoinRoomRequest;
import com.wordrace.api.request.user.UserPostRequest;
import com.wordrace.api.request.user.UserPostScoreRequest;
import com.wordrace.api.request.user.UserPutRequest;
import com.wordrace.api.response.DataResponse;
import com.wordrace.api.response.BaseResponse;

import java.util.List;
import java.util.UUID;

public interface UserService {
    DataResponse<List<UserDto>> getAllUsers();
    DataResponse<UserDto> getUserById(final UUID id);
    DataResponse<List<GameDto>> getAllGamesByUserId(final UUID userId);
    DataResponse<List<RoomDto>> getAllRoomsByUserId(final UUID userId);
    DataResponse<UserDto> createUser(final UserPostRequest userPostRequest);
    DataResponse<RoomDto> joinRoom(final UserPostJoinRoomRequest userPostJoinRoomRequest);
    DataResponse<RoomDto> addScoreToUser(final UserPostScoreRequest userPostScoreRequest);
    DataResponse<UserDto> updateUser(final UUID id, final UserPutRequest userPutRequest);
    BaseResponse deleteUserById(final UUID id);
}
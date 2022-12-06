package com.wordrace.api;

import com.wordrace.api.response.BaseResponse;
import com.wordrace.api.response.DataResponse;
import com.wordrace.dto.*;
import com.wordrace.api.request.user.UserPostJoinRoomRequest;
import com.wordrace.api.request.user.UserPostRequest;
import com.wordrace.api.request.user.UserPostScoreRequest;
import com.wordrace.api.request.user.UserPutRequest;
import com.wordrace.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<DataResponse<List<UserDto>>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataResponse<UserDto>> getUserById(@PathVariable("id") UUID id){
        return ResponseEntity.ok(this.userService.getUserById(id));
    }

    @GetMapping("/{id}/game")
    public ResponseEntity<DataResponse<List<GameDto>>> getAllGamesByUserId(@PathVariable("id") UUID userId){
        return ResponseEntity.ok(userService.getAllGamesByUserId(userId));
    }

    @GetMapping("/{id}/room")
    public ResponseEntity<DataResponse<List<RoomDto>>> getAllRoomsByUserId(@PathVariable("id") UUID userId){
        return ResponseEntity.ok(userService.getAllRoomsByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<DataResponse<UserDto>> createUser(@RequestBody UserPostRequest userPostRequest){
        return ResponseEntity.ok(userService.createUser(userPostRequest));
    }

    @PostMapping("/room/join")
    public ResponseEntity<DataResponse<RoomDto>> joinRoom(@RequestBody UserPostJoinRoomRequest userPostJoinRoomRequest){
        return ResponseEntity.ok(userService.joinRoom(userPostJoinRoomRequest));
    }

    @PostMapping("/score")
    public ResponseEntity<DataResponse<RoomDto>> addScoreToUser(UserPostScoreRequest userPostScoreRequest){
        return ResponseEntity.ok(userService.addScoreToUser(userPostScoreRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DataResponse<UserDto>> updateUser(@PathVariable("id") UUID id,
                                                            @RequestBody UserPutRequest userPutRequest){
        return ResponseEntity.ok(userService.updateUser(id, userPutRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> deleteUserById(@PathVariable("id") UUID id){
        return ResponseEntity.ok(userService.deleteUserById(id));
    }
}

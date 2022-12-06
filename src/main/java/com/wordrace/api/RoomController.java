package com.wordrace.api;

import com.wordrace.dto.*;
import com.wordrace.api.request.room.RoomPostRequest;
import com.wordrace.api.request.room.RoomPutRequest;
import com.wordrace.api.response.DataResponse;
import com.wordrace.api.response.BaseResponse;
import com.wordrace.service.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/room")
public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService)
    {
        this.roomService = roomService;
    }

    @GetMapping
    public ResponseEntity<DataResponse<List<RoomDto>>> getAllRooms(){
        return ResponseEntity.ok(roomService.getAllRooms());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataResponse<RoomDto>> getRoomById(@PathVariable("id") UUID id){
        return ResponseEntity.ok(roomService.getRoomById(id));
    }

    @GetMapping("/{id}/game")
    public ResponseEntity<DataResponse<GameDto>> getGameByRoomId(@PathVariable("id") UUID roomId){
        return ResponseEntity.ok(roomService.getGameByRoomId(roomId));
    }

    @GetMapping("/{id}/game/word")
    public ResponseEntity<DataResponse<List<WordDto>>> getWordsByRoomId(@PathVariable("id") UUID roomId) {
        return ResponseEntity.ok(roomService.getWordsByRoomId(roomId));
    }

    @GetMapping("/{id}/user")
    public ResponseEntity<DataResponse<List<UserDto>>> getUsersByRoomId(@PathVariable("id") UUID roomId){
        return ResponseEntity.ok(roomService.getUsersByRoomId(roomId));
    }

    @PostMapping
    public ResponseEntity<DataResponse<RoomDto>> createRoom(@RequestBody RoomPostRequest roomPostRequest){
        return ResponseEntity.ok(roomService.createRoom(roomPostRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DataResponse<RoomDto>> updateRoom(@PathVariable("id") UUID id,
                                                            @RequestBody RoomPutRequest roomPutRequest){
        return ResponseEntity.ok(roomService.updateRoomById(id, roomPutRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> deleteRoomById(@PathVariable("id") UUID id){
        return ResponseEntity.ok(roomService.deleteRoomById(id));
    }
}

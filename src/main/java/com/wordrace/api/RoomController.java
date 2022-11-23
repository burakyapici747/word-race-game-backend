package com.wordrace.api;

import com.wordrace.dto.*;
import com.wordrace.request.room.RoomPostRequest;
import com.wordrace.request.room.RoomPutRequest;
import com.wordrace.result.DataResult;
import com.wordrace.result.Result;
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
    public ResponseEntity<DataResult<List<RoomDto>>> getAllRooms(){
        return ResponseEntity.ok(roomService.getAllRooms());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataResult<RoomDto>> getRoomById(@PathVariable("id") UUID id){
        return ResponseEntity.ok(roomService.getRoomById(id));
    }

    @GetMapping("/{id}/game")
    public ResponseEntity<DataResult<GameDto>> getGameByRoomId(@PathVariable("id") UUID roomId){
        return ResponseEntity.ok(roomService.getGameByRoomId(roomId));
    }

    @GetMapping("/{id}/game/word")
    public ResponseEntity<DataResult<List<WordDto>>> getWordsByRoomId(@PathVariable("id") UUID roomId) {
        return ResponseEntity.ok(roomService.getWordsByRoomId(roomId));
    }

    @GetMapping("/{id}/user")
    public ResponseEntity<DataResult<List<UserDto>>> getUsersByRoomId(@PathVariable("id") UUID roomId){
        return ResponseEntity.ok(roomService.getUsersByRoomId(roomId));
    }

    @PostMapping
    public ResponseEntity<DataResult<RoomDto>> createRoom(@RequestBody RoomPostRequest roomPostRequest){
        return ResponseEntity.ok(roomService.createRoom(roomPostRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DataResult<RoomDto>> updateRoom(@PathVariable("id") UUID id, @RequestBody RoomPutRequest roomPutRequest){
        return ResponseEntity.ok(roomService.updateRoomById(id, roomPutRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result> deleteRoomById(UUID id){
        return ResponseEntity.ok(roomService.deleteRoomById(id));
    }

}

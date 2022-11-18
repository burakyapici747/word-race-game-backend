package com.wordrace.api;

import com.wordrace.dto.UserScoreDto;
import com.wordrace.request.userscore.UserScorePostRequest;
import com.wordrace.request.userscore.UserScorePutRequest;
import com.wordrace.result.DataResult;
import com.wordrace.result.Result;
import com.wordrace.service.UserScoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/userscore")
public class UserScoreController {

    private final UserScoreService userScoreService;

    public UserScoreController(UserScoreService userScoreService){
        this.userScoreService = userScoreService;
    }

    @GetMapping("/game/{gameId}")
    public ResponseEntity<DataResult<List<UserScoreDto>>> getAllUserScoresByGameId(@PathVariable("gameId") Long gameId){
        return ResponseEntity.ok(userScoreService.getAllUserScoresByGameId(gameId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<DataResult<List<UserScoreDto>>> getAllUserScoresByUserId(@PathVariable("userId") Long userId){
        return ResponseEntity.ok(userScoreService.getAllUserScoresByUserId(userId));
    }

    @GetMapping("/{userId}/{gameId}")
    public ResponseEntity<DataResult<UserScoreDto>> getUserScoreByUserIdAndGameId(@PathVariable("userId") Long userId, @PathVariable("gameId") Long gameId){
        return ResponseEntity.ok(userScoreService.getUserScoreByUserIdAndGameId(userId, gameId));
    }

    @PostMapping
    public ResponseEntity<DataResult<UserScoreDto>> createUserScore(@RequestBody UserScorePostRequest userScorePostRequest){
        return ResponseEntity.ok(userScoreService.createUserScore(userScorePostRequest));
    }

    @PutMapping
    public ResponseEntity<DataResult<UserScoreDto>> updateUserScore(@RequestBody UserScorePutRequest userScorePutRequest){
        return ResponseEntity.ok(userScoreService.updateUserScore(userScorePutRequest));
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Result> deleteUserScoreByUserId(@PathVariable("userId") Long userId){
        return ResponseEntity.ok(userScoreService.deleteUserScoreByUserId(userId));
    }

    @DeleteMapping("/game/{gameId}")
    public ResponseEntity<Result> deleteUserScoreByGameId(@PathVariable("gameId") Long gameId){
        return ResponseEntity.ok(userScoreService.deleteUserScoreByGameId(gameId));
    }

    @DeleteMapping("/{userId}/{gameId}")
    public ResponseEntity<Result> deleteUserScoreByUserIdAndGameId(@PathVariable("userId") Long userId, @PathVariable("gameId") Long gameId){
        return ResponseEntity.ok(userScoreService.deleteUserScoreByUserIdAndGameId(userId, gameId));
    }



}

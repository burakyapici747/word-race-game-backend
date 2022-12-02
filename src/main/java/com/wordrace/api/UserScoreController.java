package com.wordrace.api;

import com.wordrace.dto.UserScoreDto;
import com.wordrace.api.request.userscore.UserScorePostRequest;
import com.wordrace.api.request.userscore.UserScorePutRequest;
import com.wordrace.api.response.DataResponse;
import com.wordrace.api.response.BaseResponse;
import com.wordrace.service.UserScoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/userscore")
public class UserScoreController {

    private final UserScoreService userScoreService;

    public UserScoreController(UserScoreService userScoreService){
        this.userScoreService = userScoreService;
    }

    @GetMapping("/game/{gameId}")
    public ResponseEntity<DataResponse<List<UserScoreDto>>> getAllUserScoresByGameId(@PathVariable("gameId") UUID gameId){
        return ResponseEntity.ok(userScoreService.getAllUserScoresByGameId(gameId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<DataResponse<List<UserScoreDto>>> getAllUserScoresByUserId(@PathVariable("userId") UUID userId){
        return ResponseEntity.ok(userScoreService.getAllUserScoresByUserId(userId));
    }

    @GetMapping("/{userId}/{gameId}")
    public ResponseEntity<DataResponse<UserScoreDto>> getUserScoreByUserIdAndGameId(@PathVariable("userId") UUID userId,
                                                                                    @PathVariable("gameId") UUID gameId){
        return ResponseEntity.ok(userScoreService.getUserScoreByUserIdAndGameId(userId, gameId));
    }

    @PostMapping
    public ResponseEntity<DataResponse<UserScoreDto>> createUserScore(@RequestBody UserScorePostRequest userScorePostRequest){
        return ResponseEntity.ok(userScoreService.createUserScore(userScorePostRequest));
    }

    @PutMapping
    public ResponseEntity<DataResponse<UserScoreDto>> updateUserScore(@RequestBody UserScorePutRequest userScorePutRequest){
        return ResponseEntity.ok(userScoreService.updateUserScore(userScorePutRequest));
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<BaseResponse> deleteUserScoreByUserId(@PathVariable("userId") UUID userId){
        return ResponseEntity.ok(userScoreService.deleteUserScoreByUserId(userId));
    }

    @DeleteMapping("/game/{gameId}")
    public ResponseEntity<BaseResponse> deleteUserScoreByGameId(@PathVariable("gameId") UUID gameId){
        return ResponseEntity.ok(userScoreService.deleteUserScoreByGameId(gameId));
    }

    @DeleteMapping("/{userId}/{gameId}")
    public ResponseEntity<BaseResponse> deleteUserScoreByUserIdAndGameId(@PathVariable("userId") UUID userId, @PathVariable("gameId") UUID gameId){
        return ResponseEntity.ok(userScoreService.deleteUserScoreByUserIdAndGameId(userId, gameId));
    }



}

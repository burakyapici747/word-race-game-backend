package com.wordrace.api;

import com.wordrace.dto.GameDto;
import com.wordrace.dto.RoomDto;
import com.wordrace.dto.UserDto;
import com.wordrace.dto.WordDto;
import com.wordrace.api.request.game.GamePostRequest;
import com.wordrace.api.request.game.GamePutRequest;
import com.wordrace.api.response.DataResponse;
import com.wordrace.api.response.BaseResponse;
import com.wordrace.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/game")
public class GameController {
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping()
    public ResponseEntity<DataResponse<List<GameDto>>> getAllGames(){
        return ResponseEntity.ok(gameService.getAllGames());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<DataResponse<GameDto>> getGameById(@PathVariable("id") UUID id){
        return ResponseEntity.ok(gameService.getGameById(id));
    }

    @GetMapping(path = "/{id}/word")
    public ResponseEntity<DataResponse<List<WordDto>>> getAllWordsByGameId(@PathVariable("id") UUID gameId){
        return ResponseEntity.ok(gameService.getAllWordsByGameId(gameId));
    }

    @GetMapping(path = "/{id}/room")
    public ResponseEntity<DataResponse<RoomDto>> getRoomByGameId(@PathVariable("id") UUID gameId){
        return ResponseEntity.ok(gameService.getRoomByGameId(gameId));
    }

    @GetMapping(path = "/{id}/user")
    public ResponseEntity<DataResponse<List<UserDto>>> getAllUsersByGameId(@PathVariable("id") UUID gameId) {
        return ResponseEntity.ok(gameService.getAllUsersByGameId(gameId));
    }

    @PostMapping()
    public ResponseEntity<DataResponse<GameDto>> createGame(@RequestBody GamePostRequest gamePostRequest){
        return ResponseEntity.ok(gameService.createGame(gamePostRequest));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<DataResponse<GameDto>> updateTotalScoreByGameId(@PathVariable("id") UUID gameId,
                                                                          @RequestBody GamePutRequest gamePutRequest){
        return ResponseEntity.ok(gameService.updateTotalScoreByGameId(gameId, gamePutRequest));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<BaseResponse> deleteGameById(@PathVariable("id") UUID id){
        return ResponseEntity.ok(gameService.deleteGameById(id));
    }
}

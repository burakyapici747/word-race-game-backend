package com.wordrace.api;

import com.wordrace.dto.GameDto;
import com.wordrace.dto.RoomDto;
import com.wordrace.dto.UserDto;
import com.wordrace.dto.WordDto;
import com.wordrace.request.game.GamePostRequest;
import com.wordrace.request.game.GamePostWordRequest;
import com.wordrace.request.game.GamePutRequest;
import com.wordrace.result.DataResult;
import com.wordrace.result.Result;
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
    public ResponseEntity<DataResult<List<GameDto>>> getAllGames(){
        return ResponseEntity.ok(gameService.getAllGames());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<DataResult<GameDto>> getGameById(@PathVariable("id") UUID id){
        return ResponseEntity.ok(gameService.getGameById(id));
    }

    @GetMapping(path = "/{id}/word")
    public ResponseEntity<DataResult<List<WordDto>>> getAllWordsByGameId(@PathVariable("id") UUID gameId){
        return ResponseEntity.ok(gameService.getAllWordsByGameId(gameId));
    }

    @GetMapping(path = "/{id}/room")
    public ResponseEntity<DataResult<RoomDto>> getRoomByGameId(@PathVariable("id") UUID gameId){
        return ResponseEntity.ok(gameService.getRoomByGameId(gameId));
    }

    @GetMapping(path = "/{id}/user")
    public ResponseEntity<DataResult<List<UserDto>>> getAllUsersByGameId(@PathVariable("id") UUID gameId) {
        return ResponseEntity.ok(gameService.getAllUsersByGameId(gameId));
    }

    @PostMapping()
    public ResponseEntity<DataResult<GameDto>> createGame(@RequestBody GamePostRequest gamePostRequest){
        return ResponseEntity.ok(gameService.createGame(gamePostRequest));
    }

    @PostMapping(path = "/{id}/word")
    public ResponseEntity<DataResult<GameDto>> addWordToGameByGameId(@PathVariable("id") UUID gameId, @RequestBody GamePostWordRequest gamePostWordRequest){
        return ResponseEntity.ok(gameService.addWordToGameByGameId(gameId, gamePostWordRequest));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<DataResult<GameDto>> updateTotalScoreByGameId(@PathVariable("id") UUID gameId, @RequestBody GamePutRequest gamePutRequest){
        return ResponseEntity.ok(gameService.updateTotalScoreByGameId(gameId, gamePutRequest));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Result> deleteGameById(@PathVariable("id") UUID id){
        return ResponseEntity.ok(gameService.deleteGameById(id));
    }
}

package com.wordrace.api;

import com.wordrace.dto.GameDto;
import com.wordrace.dto.WordDto;
import com.wordrace.api.request.word.WordPostGameRequest;
import com.wordrace.api.request.word.WordPostRequest;
import com.wordrace.api.request.word.WordPutRequest;
import com.wordrace.api.response.DataResponse;
import com.wordrace.api.response.BaseResponse;
import com.wordrace.service.WordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/word")
public class WordController {
    private final WordService wordService;

    public WordController(WordService wordService) {
        this.wordService = wordService;
    }

    @GetMapping()
    public ResponseEntity<DataResponse<List<WordDto>>> getAllWords(){
        return ResponseEntity.ok(wordService.getAllWords());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataResponse<WordDto>> getWordById(@PathVariable("id") UUID id){
        return ResponseEntity.ok(wordService.getWordById(id));
    }

    @PostMapping
    public ResponseEntity<DataResponse<WordDto>> createWord(@RequestBody WordPostRequest wordPostRequest){
        return ResponseEntity.ok(wordService.createWord(wordPostRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DataResponse<WordDto>> updateWordById(@PathVariable("id") UUID id,
                                                                @RequestBody WordPutRequest wordPutRequest){
        return ResponseEntity.ok(wordService.updateWordById(id, wordPutRequest));
    }

    @PostMapping(path = "/addwordstogame/{id}")
    public ResponseEntity<DataResponse<GameDto>> addWordToGameByGameId(@PathVariable("id") UUID gameId,
                                                                       @RequestBody WordPostGameRequest wordPostGameRequest){
        return ResponseEntity.ok(wordService.addWordToGameByGameId(gameId, wordPostGameRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> deleteWordById(@PathVariable UUID id){
        return ResponseEntity.ok(wordService.deleteWordById(id));
    }
}

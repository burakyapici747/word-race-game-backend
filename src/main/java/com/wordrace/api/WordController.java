package com.wordrace.api;

import com.wordrace.dto.WordDto;
import com.wordrace.request.word.WordPostRequest;
import com.wordrace.request.word.WordPutRequest;
import com.wordrace.result.DataResult;
import com.wordrace.result.Result;
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
    public ResponseEntity<DataResult<List<WordDto>>> getAllWords(){
        return ResponseEntity.ok(wordService.getAllWords());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataResult<WordDto>> getWordById(@PathVariable("id") UUID id){
        return ResponseEntity.ok(wordService.getWordById(id));
    }

    @PostMapping
    public ResponseEntity<DataResult<WordDto>> createWord(@RequestBody WordPostRequest wordPostRequest){
        return ResponseEntity.ok(wordService.createWord(wordPostRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DataResult<WordDto>> updateWordById(@PathVariable("id") UUID id, @RequestBody WordPutRequest wordPutRequest){
        return ResponseEntity.ok(wordService.updateWordById(id, wordPutRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Result> deleteWordById(@PathVariable UUID id){
        return ResponseEntity.ok(wordService.deleteWordById(id));
    }

}

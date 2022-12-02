package com.wordrace.service;

import com.wordrace.dto.GameDto;
import com.wordrace.dto.WordDto;
import com.wordrace.model.Word;
import com.wordrace.request.word.WordPostGameRequest;
import com.wordrace.request.word.WordPostRequest;
import com.wordrace.request.word.WordPutRequest;
import com.wordrace.result.DataResult;
import com.wordrace.result.Result;

import java.util.List;
import java.util.UUID;

public interface WordService {
    DataResult<List<WordDto>> getAllWords();
    DataResult<WordDto> getWordById(final UUID id);
    DataResult<WordDto> createWord(final WordPostRequest wordPostRequest);
    DataResult<WordDto> updateWordById(final UUID id, final WordPutRequest wordPutRequest);
    DataResult<GameDto> addWordToGameByGameId(final UUID gameId, final WordPostGameRequest wordPostGameRequest);
    Result deleteWordById(final UUID id);
}
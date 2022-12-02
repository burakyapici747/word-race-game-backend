package com.wordrace.service;

import com.wordrace.dto.GameDto;
import com.wordrace.dto.WordDto;
import com.wordrace.api.request.word.WordPostGameRequest;
import com.wordrace.api.request.word.WordPostRequest;
import com.wordrace.api.request.word.WordPutRequest;
import com.wordrace.api.response.DataResponse;
import com.wordrace.api.response.BaseResponse;

import java.util.List;
import java.util.UUID;

public interface WordService {
    DataResponse<List<WordDto>> getAllWords();
    DataResponse<WordDto> getWordById(final UUID id);
    DataResponse<WordDto> createWord(final WordPostRequest wordPostRequest);
    DataResponse<WordDto> updateWordById(final UUID id, final WordPutRequest wordPutRequest);
    DataResponse<GameDto> addWordToGameByGameId(final UUID gameId, final WordPostGameRequest wordPostGameRequest);
    BaseResponse deleteWordById(final UUID id);
}
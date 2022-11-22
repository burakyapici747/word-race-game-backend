package com.wordrace.service.impl;

import com.wordrace.constant.ResultMessages;
import com.wordrace.constant.RoomMessages;
import com.wordrace.dto.GameDto;
import com.wordrace.dto.RoomDto;
import com.wordrace.dto.UserDto;
import com.wordrace.dto.WordDto;
import com.wordrace.exception.EntityAlreadyExistException;
import com.wordrace.exception.EntityNotFoundException;
import com.wordrace.model.*;
import com.wordrace.repository.GameRepository;
import com.wordrace.repository.RoomRepository;
import com.wordrace.repository.WordRepository;
import com.wordrace.request.game.GamePostRequest;
import com.wordrace.request.game.GamePostWordRequest;
import com.wordrace.request.game.GamePutRequest;
import com.wordrace.result.DataResult;
import com.wordrace.result.Result;
import com.wordrace.result.SuccessDataResult;
import com.wordrace.result.SuccessResult;
import com.wordrace.service.GameService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final RoomRepository roomRepository;
    private final WordRepository wordRepository;
    private final ModelMapper modelMapper;

    public GameServiceImpl(GameRepository gameRepository,
                           RoomRepository roomRepository,
                           WordRepository wordRepository,
                           ModelMapper modelMapper) {
        this.gameRepository = gameRepository;
        this.roomRepository = roomRepository;
        this.wordRepository = wordRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public DataResult<List<GameDto>> getAllGames() {
        final List<GameDto> gameDtos = gameRepository.findAll()
                .stream()
                .map(game -> modelMapper.map(game, GameDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(gameDtos, ResultMessages.EMPTY);
    }

    @Override
    public DataResult<GameDto> getGameById(UUID id) {
        final Game game = findById(id);
        final GameDto gameDto = modelMapper.map(game, GameDto.class);
        
        return new SuccessDataResult<>(gameDto, ResultMessages.EMPTY);
    }

    @Override
    public DataResult<List<WordDto>> getAllWordsByGameId(UUID gameId) {
        final Game game = findById(gameId);
        final List<WordDto> wordDtos = game.getWords()
                .stream()
                .map(word-> modelMapper.map(word, WordDto.class))
                .collect(Collectors.toList());
        
        return new SuccessDataResult<>(wordDtos, ResultMessages.EMPTY);
    }

    @Override
    public DataResult<RoomDto> getRoomByGameId(UUID gameId) {
        final Game game = findById(gameId);
        final Room room = game.getRoom();
        final RoomDto roomDto = modelMapper.map(room, RoomDto.class);
        
        return new SuccessDataResult<>(roomDto, ResultMessages.EMPTY);
    }

    @Override
    public DataResult<List<UserDto>> getAllUsersByGameId(UUID gameId) {
        final Game game = findById(gameId);
        final Room room = game.getRoom();
        final List<UserDto> userDtos = room.getUsers()
                .stream()
                .map(user-> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
        
        return new SuccessDataResult<>(userDtos, ResultMessages.EMPTY);
    }

    @Override
    public DataResult<GameDto> createGame(GamePostRequest gamePostRequest) {
        final Room room = findRoomById(UUID.fromString(gamePostRequest.getRoomId()));
        final Game game = new Game();

        if(Optional.ofNullable(room.getGame()).isPresent()){
            throw new EntityAlreadyExistException(RoomMessages.ROOM_HAS_ALREADY_GAME);
        }
        
        game.setRoom(room);
        
        final GameDto gameDto = modelMapper.map(gameRepository.save(game), GameDto.class);
        
        return new SuccessDataResult<>(gameDto, ResultMessages.SUCCESS_CREATE);
    }

    @Override
    public DataResult<GameDto> addWordToGameByGameId(UUID gameId, GamePostWordRequest gamePostWordRequest) {
        final Game game = findById(gameId);
        gamePostWordRequest.getWordIds().forEach(wordId -> {

            boolean anySameWordInGame = game.getWords().stream()
                    .filter(gameWord -> gameWord.getId().equals(wordId))
                    .collect(Collectors.toList()).size() > 0;

            if(!anySameWordInGame){
                final Word word = findWordById(UUID.fromString(wordId));

                word.getGames().add(game);
                wordRepository.save(word);
            }
        });

        return new SuccessDataResult<>(modelMapper.map(game, GameDto.class), ResultMessages.EMPTY);
    }

    @Override
    public DataResult<GameDto> updateTotalScoreByGameId(UUID gameId, GamePutRequest gamePutRequest) {
        final Game game = findById(gameId);
        
        game.setTotalScore(gamePutRequest.getTotalScore());
        
        final GameDto gameDto = modelMapper.map(gameRepository.save(game), GameDto.class);
        
        return new SuccessDataResult<>(gameDto, ResultMessages.SUCCESS_UPDATE);
    }

    @Override
    public Result deleteGameById(UUID id) {
        final Game game = findById(id);
        
        gameRepository.delete(game);
        
        return new SuccessResult(ResultMessages.SUCCESS_DELETE);
    }

    private Game findById(UUID id){
        return gameRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ResultMessages.NOT_FOUND_DATA));
    }

    private Room findRoomById(UUID id){
        return roomRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(ResultMessages.NOT_FOUND_DATA));
    }

    private Word findWordById(UUID id){
        return wordRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(ResultMessages.NOT_FOUND_DATA));
    }
}

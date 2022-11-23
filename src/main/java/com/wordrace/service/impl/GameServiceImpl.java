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
import com.wordrace.util.GlobalHelper;
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
        final List<GameDto> gameDtos = GlobalHelper.listDtoConverter(modelMapper,
                gameRepository.findAll(), GameDto.class);

        return new SuccessDataResult<>(gameDtos, ResultMessages.EMPTY);
    }

    @Override
    public DataResult<GameDto> getGameById(final UUID id) {
        final Game game = findById(id);
        final GameDto gameDto = modelMapper.map(game, GameDto.class);
        
        return new SuccessDataResult<>(gameDto, ResultMessages.EMPTY);
    }

    @Override
    public DataResult<List<WordDto>> getAllWordsByGameId(final UUID gameId) {
        final Game game = findById(gameId);
        final List<WordDto> wordDtos = GlobalHelper.listDtoConverter(modelMapper, game.getWords(), WordDto.class);

        return new SuccessDataResult<>(wordDtos, ResultMessages.EMPTY);
    }

    @Override
    public DataResult<RoomDto> getRoomByGameId(final UUID gameId) {
        final Game game = findById(gameId);
        final Room room = game.getRoom();
        final RoomDto roomDto = modelMapper.map(room, RoomDto.class);
        
        return new SuccessDataResult<>(roomDto, ResultMessages.EMPTY);
    }

    @Override
    public DataResult<List<UserDto>> getAllUsersByGameId(final UUID gameId) {
        final Game game = findById(gameId);
        final Room room = game.getRoom();
        final List<UserDto> userDtos = GlobalHelper.listDtoConverter(modelMapper, room.getUsers(), UserDto.class);

        return new SuccessDataResult<>(userDtos, ResultMessages.EMPTY);
    }

    @Override
    public DataResult<GameDto> createGame(final GamePostRequest gamePostRequest) {
        final Room room = findRoomById(UUID.fromString(gamePostRequest.getRoomId()));
        final Game game = new Game();

        GlobalHelper.checkIfAlreadyExist(room.getGame());
        
        game.setRoom(room);
        
        final GameDto gameDto = modelMapper.map(gameRepository.save(game), GameDto.class);
        
        return new SuccessDataResult<>(gameDto, ResultMessages.SUCCESS_CREATE);
    }

    @Override
    public DataResult<GameDto> addWordToGameByGameId(final UUID gameId, final GamePostWordRequest gamePostWordRequest) {
        final Game game = findById(gameId);

        gamePostWordRequest.getWordIds().forEach(wordId -> {
            if(checkAnySameWordInGame(game,UUID.fromString(wordId))){
                final Word word = findWordById(UUID.fromString(wordId));

                word.getGames().add(game);
                wordRepository.save(word);
            }
        });

        return new SuccessDataResult<>(modelMapper.map(game, GameDto.class), ResultMessages.EMPTY);
    }

    @Override
    public DataResult<GameDto> updateTotalScoreByGameId(final UUID gameId, final GamePutRequest gamePutRequest) {
        final Game game = findById(gameId);
        
        game.setTotalScore(gamePutRequest.getTotalScore());
        
        final GameDto gameDto = modelMapper.map(gameRepository.save(game), GameDto.class);
        
        return new SuccessDataResult<>(gameDto, ResultMessages.SUCCESS_UPDATE);
    }

    @Override
    public Result deleteGameById(final UUID id) {
        final Game game = findById(id);
        
        gameRepository.delete(game);
        
        return new SuccessResult(ResultMessages.SUCCESS_DELETE);
    }

    private Game findById(final UUID id){
        return gameRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ResultMessages.NOT_FOUND_DATA));
    }

    private Room findRoomById(final UUID id){
        return roomRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(ResultMessages.NOT_FOUND_DATA));
    }

    private Word findWordById(final UUID id){
        return wordRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(ResultMessages.NOT_FOUND_DATA));
    }

    private boolean checkAnySameWordInGame(final Game game, final UUID wordId){
        return game.getWords().stream().anyMatch(gameWord -> gameWord.getId().equals(wordId));
    }
}

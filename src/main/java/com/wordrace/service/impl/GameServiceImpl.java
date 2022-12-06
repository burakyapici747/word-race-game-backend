package com.wordrace.service.impl;

import com.wordrace.constant.ResponseConstant;
import com.wordrace.dto.GameDto;
import com.wordrace.dto.RoomDto;
import com.wordrace.dto.UserDto;
import com.wordrace.dto.WordDto;
import com.wordrace.exception.EntityNotFoundException;
import com.wordrace.model.*;
import com.wordrace.repository.GameRepository;
import com.wordrace.api.request.game.GamePostRequest;
import com.wordrace.api.request.game.GamePutRequest;
import com.wordrace.api.response.DataResponse;
import com.wordrace.api.response.BaseResponse;
import com.wordrace.api.response.SuccessDataResponse;
import com.wordrace.api.response.SuccessResponse;
import com.wordrace.service.GameService;
import com.wordrace.util.GlobalHelper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final RoomServiceImpl roomService;
    private final ModelMapper modelMapper;

    public GameServiceImpl(GameRepository gameRepository,
                           RoomServiceImpl roomService,
                           ModelMapper modelMapper) {
        this.gameRepository = gameRepository;
        this.roomService = roomService;
        this.modelMapper = modelMapper;
    }

    @Override
    public DataResponse<List<GameDto>> getAllGames() {
        final List<GameDto> gameDtoList = GlobalHelper.listDtoConverter(modelMapper,
                gameRepository.findAll(), GameDto.class);

        return new SuccessDataResponse<>(gameDtoList, ResponseConstant.EMPTY);
    }

    @Override
    public DataResponse<GameDto> getGameById(final UUID id) {
        final Game game = findGameById(id);
        final GameDto gameDto = modelMapper.map(game, GameDto.class);
        
        return new SuccessDataResponse<>(gameDto, ResponseConstant.EMPTY);
    }

    @Override
    public DataResponse<List<WordDto>> getAllWordsByGameId(final UUID gameId) {
        final Game game = findGameById(gameId);
        final List<WordDto> wordDtoList = GlobalHelper.listDtoConverter(modelMapper, game.getWords(), WordDto.class);

        return new SuccessDataResponse<>(wordDtoList, ResponseConstant.EMPTY);
    }

    @Override
    public DataResponse<RoomDto> getRoomByGameId(final UUID gameId) {
        final Game game = findGameById(gameId);
        final Room room = game.getRoom();
        final RoomDto roomDto = modelMapper.map(room, RoomDto.class);
        
        return new SuccessDataResponse<>(roomDto, ResponseConstant.EMPTY);
    }

    @Override
    public DataResponse<List<UserDto>> getAllUsersByGameId(final UUID gameId) {
        final Game game = findGameById(gameId);
        final Room room = game.getRoom();
        final List<UserDto> userDtos = GlobalHelper.listDtoConverter(modelMapper, room.getUsers(), UserDto.class);

        return new SuccessDataResponse<>(userDtos, ResponseConstant.EMPTY);
    }

    @Override
    public DataResponse<GameDto> createGame(final GamePostRequest gamePostRequest) {
        final Room room = roomService.findRoomById(UUID.fromString(gamePostRequest.getRoomId()));
        final Game game = new Game();

        GlobalHelper.checkIfAlreadyExist(room.getGame());
        
        game.setRoom(room);
        
        final GameDto gameDto = modelMapper.map(gameRepository.save(game), GameDto.class);
        
        return new SuccessDataResponse<>(gameDto, ResponseConstant.SUCCESS_CREATE);
    }

    @Override
    public DataResponse<GameDto> updateTotalScoreByGameId(final UUID gameId, final GamePutRequest gamePutRequest) {
        final Game game = findGameById(gameId);
        
        game.setTotalScore(gamePutRequest.getTotalScore());
        
        final GameDto gameDto = modelMapper.map(gameRepository.save(game), GameDto.class);
        
        return new SuccessDataResponse<>(gameDto, ResponseConstant.SUCCESS_UPDATE);
    }

    @Override
    public BaseResponse deleteGameById(final UUID id) {
        final Game game = findGameById(id);
        
        gameRepository.delete(game);
        
        return new SuccessResponse(ResponseConstant.SUCCESS_DELETE);
    }

    protected Game findGameById(final UUID id){
        return gameRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ResponseConstant.NOT_FOUND_DATA));
    }

}

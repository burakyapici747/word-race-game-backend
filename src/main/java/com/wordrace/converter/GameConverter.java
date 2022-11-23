package com.wordrace.converter;

import com.wordrace.dto.GameDto;
import com.wordrace.dto.RoomDto;
import com.wordrace.dto.UserScoreDto;
import com.wordrace.dto.WordDto;
import com.wordrace.model.Game;
import com.wordrace.util.GlobalHelper;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GameConverter implements Converter<Game, GameDto> {

    private final ModelMapper modelMapper;

    public GameConverter(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    @Override
    public GameDto convert(MappingContext<Game, GameDto> mappingContext) {
        final Game game = mappingContext.getSource();
        final RoomDto roomDto = modelMapper.map(game.getRoom(), RoomDto.class);
        final List<UserScoreDto> userScoreDtos = GlobalHelper.listDtoConverter(modelMapper,
                game.getUserScores(), UserScoreDto.class);
        final List<WordDto> wordDtos = GlobalHelper.listDtoConverter(modelMapper,
                game.getWords(), WordDto.class);
        final GameDto gameDto = new GameDto();

        gameDto.setId(game.getId().toString());
        gameDto.setRoom(roomDto);
        gameDto.setUserScores(userScoreDtos);
        gameDto.setWords(wordDtos);
        gameDto.setTotalScore(game.getTotalScore());

        return gameDto;
    }

}

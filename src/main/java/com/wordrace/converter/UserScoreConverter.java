package com.wordrace.converter;

import com.wordrace.dto.GameDto;
import com.wordrace.dto.UserDto;
import com.wordrace.dto.UserScoreDto;
import com.wordrace.model.UserScore;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

@Component
public class UserScoreConverter implements Converter<UserScore, UserScoreDto> {

    private final ModelMapper modelMapper;

    public UserScoreConverter(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    @Override
    public UserScoreDto convert(MappingContext<UserScore, UserScoreDto> mappingContext) {
        final UserScore userScore = mappingContext.getSource();
        final GameDto gameDto = modelMapper.map(userScore.getGame(), GameDto.class);
        final UserDto userDto = modelMapper.map(userScore.getUser(), UserDto.class);
        final UserScoreDto userScoreDto = new UserScoreDto();

        userScoreDto.setId(userScore.getId().toString());
        userScoreDto.setGame(gameDto);
        userScoreDto.setUser(userDto);
        userScoreDto.setScore(userScore.getScore());

        return userScoreDto;
    }
}

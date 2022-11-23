package com.wordrace.config;

import com.wordrace.converter.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@RequiredArgsConstructor
public class MapperConfig {

    private final GameConverter gameConverter;
    private final RoomConverter roomConverter;
    private final UserConverter userConverter;
    private final UserScoreConverter userScoreConverter;
    private final WordConverter wordConverter;

    @Bean
    @Scope
    public ModelMapper getModelMapper(){
        final ModelMapper modelMapper = new ModelMapper();

        modelMapper.addConverter(gameConverter);
        modelMapper.addConverter(roomConverter);
        modelMapper.addConverter(userConverter);
        modelMapper.addConverter(userScoreConverter);
        modelMapper.addConverter(wordConverter);

        return modelMapper;
    }
}

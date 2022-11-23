package com.wordrace.converter;

import com.wordrace.dto.UserDto;
import com.wordrace.model.User;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

@Component
public class UserConverter implements Converter<User, UserDto> {

    private final ModelMapper modelMapper;

    public UserConverter(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDto convert(MappingContext<User, UserDto> mappingContext) {
        final User user = mappingContext.getSource();
        final UserDto userDto = new UserDto();

        userDto.setId(user.getId().toString());
        userDto.setEmail(user.getEmail());
        userDto.setNickName(user.getNickName());

        return userDto;
    }

}

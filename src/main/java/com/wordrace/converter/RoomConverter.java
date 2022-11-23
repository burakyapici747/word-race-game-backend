package com.wordrace.converter;

import com.wordrace.dto.GameDto;
import com.wordrace.dto.RoomDto;
import com.wordrace.dto.UserDto;
import com.wordrace.exception.EntityNotFoundException;
import com.wordrace.model.Room;
import com.wordrace.model.User;
import com.wordrace.repository.UserRepository;
import com.wordrace.util.GlobalHelper;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class RoomConverter implements Converter<Room, RoomDto> {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public RoomConverter(ModelMapper modelMapper, UserRepository userRepository){
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Override
    public RoomDto convert(MappingContext<Room, RoomDto> mappingContext) {
        final Room room = mappingContext.getSource();
        final UserDto createrUserDto = modelMapper.map(findUserById(room.getCreatorId()), UserDto.class);
        final UserDto winnderUserDto = modelMapper.map(findUserById(room.getWinnerId()), UserDto.class);
        final GameDto gameDto = modelMapper.map(room.getGame(), GameDto.class);
        final List<UserDto> userDtos = GlobalHelper.listDtoConverter(modelMapper, room.getUsers(), UserDto.class);
        final RoomDto roomDto = new RoomDto();

        roomDto.setCreatorUser(createrUserDto);
        roomDto.setWinnerUser(winnderUserDto);
        roomDto.setGame(gameDto);
        roomDto.setUsers(userDtos);
        roomDto.setId(room.getId().toString());
        roomDto.setRoomName(room.getRoomName());
        roomDto.setCapacity(room.getCapacity());

        return roomDto;
    }

    private User findUserById(UUID userId){
        return userRepository.findById(userId)
                .orElse(null);
    }

}

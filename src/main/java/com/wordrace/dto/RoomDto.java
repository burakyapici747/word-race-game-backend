package com.wordrace.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
public class RoomDto {
    private String id;
    private UUID creatorId;
    private UUID winnerId;
    private String roomName;
    private int capacity;
    private GameDto game;
    private List<UserDto> users;

}

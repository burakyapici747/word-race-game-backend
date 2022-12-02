package com.wordrace.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class GameRoomDto {
    private String id;
    private UUID creatorId;
    private UUID winnerId;
    private String roomName;
    private int capacity;
    private List<UserDto> users;
}

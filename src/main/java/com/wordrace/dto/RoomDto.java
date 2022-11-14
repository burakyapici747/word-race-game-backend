package com.wordrace.dto;

import java.util.List;

public class RoomDto {

    private Long id;
    private UserDto creatorUser;
    private UserDto winnerUser;
    private String roomName;
    private int capacity;
    private GameDto game;
    private List<UserDto> users;

}

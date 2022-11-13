package com.wordrace.request.room;

import lombok.Data;

@Data
public class RoomPostRequest{
    private Long creatorId;
    private String roomName;
    private int capacity;
}
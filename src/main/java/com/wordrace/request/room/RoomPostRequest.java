package com.wordrace.request.room;

import lombok.Data;

@Data
public class RoomPostRequest{
    private String creatorId;
    private String roomName;
    private int capacity;
}
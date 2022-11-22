package com.wordrace.request.user;

import lombok.Data;

@Data
public class UserPostJoinRoomRequest {
    private String roomId;
    private String userId;
}

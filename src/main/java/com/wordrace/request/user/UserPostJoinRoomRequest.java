package com.wordrace.request.user;

import lombok.Data;

@Data
public class UserPostJoinRoomRequest {
    private Long roomId;
    private Long userId;
}

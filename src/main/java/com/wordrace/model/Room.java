package com.wordrace.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Room {

    @Id
    private Long id;

    private Long creatorId;

    private Long winnerId;

    private String roomName;

    private int capacity;

}

package com.wordrace.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "room")
public class Room extends BaseEntity{

    @Column(name = "creator_id")
    private UUID creatorId;

    @Column(name = "winner_id")
    private UUID winnerId;

    @Column(name = "room_name")
    private String roomName;

    @Column(name = "capacity")
    private int capacity;

    @OneToOne(mappedBy = "room")
    private Game game;

    @ManyToMany(mappedBy = "rooms",fetch = FetchType.LAZY)
    private List<User> users;

}

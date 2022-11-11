package com.wordrace.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long creatorId;

    private Long winnerId;

    private String roomName;

    private int capacity;

    @OneToOne(mappedBy = "room")
    private Game game;


    @ManyToMany(mappedBy = "rooms",fetch = FetchType.LAZY)
    private List<User> users;

}

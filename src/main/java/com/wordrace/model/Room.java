package com.wordrace.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "creator_id")
    private Long creatorId;

    @Column(name = "winner_id")
    private Long winnerId;

    @Column(name = "room_name")
    private String roomName;

    @Column(name = "capacity")
    private int capacity;

    @OneToOne(mappedBy = "room")
    private Game game;

    @ManyToMany(mappedBy = "rooms",fetch = FetchType.LAZY)
    private List<User> users;

}

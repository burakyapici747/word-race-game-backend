package com.wordrace.model;


import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String email;

    private String password;

    private String nickname;

    @OneToMany(mappedBy = "user")
    private List<UserScore> userScore;


    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Room.class)
    @JoinTable(name = "room_user",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "room_id"))
    private List<Room> rooms;

}

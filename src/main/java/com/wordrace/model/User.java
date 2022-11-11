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

    @OneToMany(mappedBy = "user")
    private List<UserScore> userScore;

    private String email;
    private String password;
    private String nickname;


    @ManyToMany(targetEntity = Room.class)
    private List<Room> rooms;

}

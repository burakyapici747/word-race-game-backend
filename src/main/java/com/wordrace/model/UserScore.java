package com.wordrace.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class UserScore {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private int sore;

}

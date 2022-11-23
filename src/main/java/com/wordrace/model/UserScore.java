package com.wordrace.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user_score")
public class UserScore extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "score")
    private int score;

}

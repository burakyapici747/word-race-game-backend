package com.wordrace.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "game")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "roomId", referencedColumnName = "id")
    private Room room;

    @OneToMany(mappedBy = "game")
    private List<UserScore> userScore;

    @ManyToMany(mappedBy = "games")
    private List<Word> words;

    @Column(name = "total_score")
    private int totalScore;

}

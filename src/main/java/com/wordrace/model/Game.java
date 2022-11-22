package com.wordrace.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "game")
public class Game extends BaseEntity{

    @OneToOne
    @JoinColumn(name = "roomId", referencedColumnName = "id")
    private Room room;

    @OneToMany(mappedBy = "game")
    private List<UserScore> userScores;

    @ManyToMany(mappedBy = "games")
    private List<Word> words;

    @Column(name = "total_score")
    private int totalScore;

}

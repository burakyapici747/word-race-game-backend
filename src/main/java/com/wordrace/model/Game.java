package com.wordrace.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table
public class Game {

    @Id
    private Long id;

    @OneToOne
    @JoinColumn(name = "roomId", referencedColumnName = "id")
    private Room room;

    @OneToMany(mappedBy = "game")
    private List<UserScore> userScore;

    @ManyToMany(targetEntity = Word.class)
    private List<Word> words;

    private int totalScore;

}

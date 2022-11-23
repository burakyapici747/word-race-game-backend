package com.wordrace.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "word")
public class Word extends BaseEntity {

    @Column(name = "text")
    private String text;

    @Column(name = "language")
    private Language language;

    @ManyToMany(targetEntity = Game.class)
    @JoinTable(name = "game_word",
            joinColumns = @JoinColumn(name = "word_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id"))
    private List<Game> games;

}

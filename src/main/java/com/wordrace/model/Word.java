package com.wordrace.model;


import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Word {

    @Id
    private Long id;

    private String text;

    private Language language;

    @ManyToMany(targetEntity = Game.class)
    @JoinTable(name = "game_word",
            joinColumns = @JoinColumn(name = "word_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id"))
    private List<Game> games;

}

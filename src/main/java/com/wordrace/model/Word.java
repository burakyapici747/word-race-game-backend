package com.wordrace.model;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.List;

@Data
@Entity
public class Word {

    @Id
    private Long id;

    @ManyToMany(targetEntity = Game.class)
    private List<Game> games;

    private String text;

    private Language language;


}

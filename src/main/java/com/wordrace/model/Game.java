package com.wordrace.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table
public class Game {

    @Id
    private Long id;
    private int totalScore;

}

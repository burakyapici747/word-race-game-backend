package com.wordrace.model;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Word {

    @Id
    private Long id;

    private String text;

    private Language language;


}

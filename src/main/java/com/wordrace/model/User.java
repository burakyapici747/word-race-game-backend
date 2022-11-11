package com.wordrace.model;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
@Data
public class User {

    @Id
    private Long id;
    private String email;
    private String password;
    private String nickname;

}

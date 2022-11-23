package com.wordrace.model;


import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "user")
public class User extends BaseEntity{

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "nickname")
    private String nickName;

    @OneToMany(mappedBy = "user")
    private List<UserScore> userScore;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Room.class)
    @JoinTable(name = "room_user",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "room_id"))
    private List<Room> rooms;

}

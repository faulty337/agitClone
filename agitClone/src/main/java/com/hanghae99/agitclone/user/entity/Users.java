package com.hanghae99.agitclone.user.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Builder
    public Users(String username, String nickname, String password){
        this.username = username;
        this.nickname = nickname;
        this.password = password;
    }

    @Builder
    public Users(String username, String nickname){
        this.username = username;
        this.nickname = nickname;
    }
}

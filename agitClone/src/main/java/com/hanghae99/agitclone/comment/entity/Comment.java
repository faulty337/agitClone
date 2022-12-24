package com.hanghae99.agitclone.comment.entity;

import com.hanghae99.agitclone.common.TimeStamped;
import com.hanghae99.agitclone.user.entity.Users;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Comment extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private Users users;

    @Column(nullable = false)
    private String content;

    public Comment(String content){

        this.content = content;

    }

    public void update(String content){

        this.content = content;
    }


}



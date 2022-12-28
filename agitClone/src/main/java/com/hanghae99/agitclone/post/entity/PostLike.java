package com.hanghae99.agitclone.post.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class PostLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    private Long postId;

    private Boolean isHate;

    public PostLike(Long userId, Long postId, boolean isHate){
        this.userId = userId;
        this.postId = postId;
        this.isHate = isHate;
    }

    public void updateIsHate(Boolean hate){
        this.isHate = hate;
    }

}

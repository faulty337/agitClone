package com.hanghae99.agitclone.agit.entity;


import com.hanghae99.agitclone.post.entity.Post;
import com.hanghae99.agitclone.user.entity.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Agit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String agitName;

    @Column(nullable = false)
    private String agitInfo;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "agitId")
    private List<AgitMember> agitMemberList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "agitId")
    private List<Post> PostList = new ArrayList<>();

    public void addPostList(Post post) {
        this.PostList.add(post);
    }
    @Builder
    public Agit(String agitName, String agitInfo){
        this.agitName = agitName;
        this.agitInfo = agitInfo;
    }

}

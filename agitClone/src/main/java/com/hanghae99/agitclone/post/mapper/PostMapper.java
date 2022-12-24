package com.hanghae99.agitclone.post.mapper;

import com.hanghae99.agitclone.post.dto.RequestPostDto;
import com.hanghae99.agitclone.post.dto.ResponsePostDto;
import com.hanghae99.agitclone.post.entity.Post;
import com.hanghae99.agitclone.user.entity.Users;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    //Dto -> Entity -> 받은 정보로 Post 만들어주기.
    public Post toEntity(RequestPostDto requestPostDto, Users users, long agitId) {
        return Post.builder()
                .content(requestPostDto.getContent())
                .users(users)
                .isModified(false)
                .likeCount(0)
                .hateCount(0)
                .agitId(agitId)
                .build();
    }

    //Entity -> Dto -> 받은 Post로 dto 반환하기.
    //문제1. postlike를 어떻게 반환해야 하는가..? postlike는 모르겠다. 추 후 변경한다.
    public ResponsePostDto toResponsePostDto(Post post){
        return ResponsePostDto.builder()
                .id(post.getId())
                .username(post.getUser().getUsername())
                .nickname(post.getUser().getNickname())
                .content(post.getContent())
                .likeCount(post.getLikeCount())
                .hateCount(post.getHateCount())
                .createdAt(post.getCreatedAt())
                .isModified(post.isModified())
                .build();
    }

    //전체 조회용 필요한가?


}

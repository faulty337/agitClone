package com.hanghae99.agitclone.post.mapper;

import com.hanghae99.agitclone.post.entity.PostLike;
import org.springframework.stereotype.Component;

@Component
public class PostLikeMapper {

    public PostLike toPostLike(Long userId, Long postId, boolean isHate){
        return PostLike.builder()
                .userId(userId)
                .postId(postId)
                .isHate(isHate)
                .build();
    }
}

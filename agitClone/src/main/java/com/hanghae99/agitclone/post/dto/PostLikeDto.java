package com.hanghae99.agitclone.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostLikeDto {

    private boolean likeState;

    public PostLikeDto(boolean likeState) {
        this.likeState = likeState;
    }

}

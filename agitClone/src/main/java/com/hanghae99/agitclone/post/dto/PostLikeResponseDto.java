package com.hanghae99.agitclone.post.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
public class PostLikeResponseDto {
    private Boolean postLike;
    private Long likeCount;
    private Long hateCount;

}

package com.hanghae99.agitclone.post.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
@Builder
public class ResponsePostDto {
    private Long id;
    private String username;
    private String nickname;
    private String content;
    private Long likeCount;
    private Long hateCount;
    //private boolean postLike;
    private LocalDateTime createdAt;
    private boolean isModified;
}

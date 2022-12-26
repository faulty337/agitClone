package com.hanghae99.agitclone.post.dto;

import com.hanghae99.agitclone.comment.dto.CommentResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Builder
public class ResponsePostDto {
    private Long id;
    private String username;
    private String nickname;
    private String content;
    private Long likeCount;
    private Long hateCount;
    private Boolean postLike;
    private LocalDateTime createdAt;
    private boolean isModified;

    private List<CommentResponseDto> commentList = new ArrayList<>();
}

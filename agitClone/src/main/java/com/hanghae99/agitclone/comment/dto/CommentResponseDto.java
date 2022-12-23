package com.hanghae99.agitclone.comment.dto;

import com.hanghae99.agitclone.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class CommentResponseDto {

    private Long commentId;

    private String username;

    private String content;

    private String nickname;

    private LocalDateTime createdAt;

    public CommentResponseDto(Comment comment){

        this.commentId = comment.getId();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
    }
}

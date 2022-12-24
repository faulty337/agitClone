package com.hanghae99.agitclone.comment.mapper;

import com.hanghae99.agitclone.comment.dto.CommentRequestDto;
import com.hanghae99.agitclone.comment.dto.CommentResponseDto;
import com.hanghae99.agitclone.comment.entity.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {
    public Comment toComment(CommentRequestDto requestDto) {
        return new Comment(requestDto.getContent());
    }

    public CommentResponseDto toResponse(Comment comment) {
        return CommentResponseDto.builder()
                .commentId(comment.getId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}

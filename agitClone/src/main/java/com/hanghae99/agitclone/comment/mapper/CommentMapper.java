package com.hanghae99.agitclone.comment.mapper;

import com.hanghae99.agitclone.comment.dto.CommentRequestDto;
import com.hanghae99.agitclone.comment.dto.CommentResponseDto;
import com.hanghae99.agitclone.comment.entity.Comment;
import com.hanghae99.agitclone.post.entity.Post;
import com.hanghae99.agitclone.user.entity.Users;
import org.apache.catalina.User;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {
    public Comment toComment(CommentRequestDto requestDto, Users users) {
        return new Comment(requestDto.getContent(), users);
    }

    public CommentResponseDto toResponse(Comment comment) {
        return CommentResponseDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .nickname(comment.getUsers().getNickname())
                .build();
    }
}

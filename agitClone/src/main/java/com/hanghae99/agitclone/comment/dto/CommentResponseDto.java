package com.hanghae99.agitclone.comment.dto;

import com.hanghae99.agitclone.common.TimeStamped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class CommentResponseDto extends TimeStamped {

    private Long id;

    private String username;

    private String content;

    private String nickname;

    private LocalDateTime createdAt;

    private boolean isModified;

}
package com.example.testserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto {
    private Long id;
    private String username;
    private String nickname;
    private String content;
    private Long likeCount;
    private Long hateCount;
    private Boolean postLike;
    private LocalDateTime createAt;
    private boolean isModified;
    private List<CommentResponseDto> commentList = new ArrayList<>();
    private String picturePath;
}

package com.hanghae99.agitclone.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MainResponseDto {
    private String agitName;
    private String agitInfo;
    private List<PostResponseDto> postList = new ArrayList<>();
}

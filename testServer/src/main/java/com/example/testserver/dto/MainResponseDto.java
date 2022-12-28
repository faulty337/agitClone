package com.example.testserver.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MainResponseDto {
    private String agitName;
    private String agitInfo;
    private List<PostResponseDto> postList = new ArrayList<>();
}

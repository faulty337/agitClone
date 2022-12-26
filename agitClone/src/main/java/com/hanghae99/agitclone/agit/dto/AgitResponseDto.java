package com.hanghae99.agitclone.agit.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AgitResponseDto {
    private Long id;
    private String agitname;
}

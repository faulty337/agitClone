package com.hanghae99.agitclone.agit.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AgitMemberResponseDto {
    public String username;
    public String nickname;
}

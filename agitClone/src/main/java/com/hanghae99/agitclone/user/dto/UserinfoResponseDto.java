package com.hanghae99.agitclone.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserinfoResponseDto {
    private String username;
    private String nickname;

    public UserinfoResponseDto(String username, String nickname) {
        this.username = username;
        this.nickname = nickname;
    }
}

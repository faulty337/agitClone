package com.hanghae99.agitclone.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostHateDto {

    private boolean hateState;

    public PostHateDto(boolean hateState) {
        this.hateState = hateState;
    }

}

package com.hanghae99.agitclone.agit.mapper;

import com.hanghae99.agitclone.agit.dto.AgitRequestDto;
import com.hanghae99.agitclone.agit.entity.Agit;
import com.hanghae99.agitclone.post.dto.RequestPostDto;
import com.hanghae99.agitclone.post.entity.Post;
import com.hanghae99.agitclone.user.entity.Users;
import org.springframework.stereotype.Component;

@Component
public class AgitMapper {
    public Agit toEntity(AgitRequestDto agitRequestDto){
        return Agit.builder()
                .agitName(agitRequestDto.getAgitname())
                .build();
    }
}
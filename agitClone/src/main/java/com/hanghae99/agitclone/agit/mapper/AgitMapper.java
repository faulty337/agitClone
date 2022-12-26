package com.hanghae99.agitclone.agit.mapper;

import com.hanghae99.agitclone.agit.dto.AgitInviteRequestDto;
import com.hanghae99.agitclone.agit.dto.AgitRequestDto;
import com.hanghae99.agitclone.agit.dto.AgitResponseDto;
import com.hanghae99.agitclone.agit.entity.Agit;
import com.hanghae99.agitclone.agit.entity.AgitMember;
import com.hanghae99.agitclone.post.dto.RequestPostDto;
import com.hanghae99.agitclone.post.dto.ResponsePostDto;
import com.hanghae99.agitclone.post.entity.Post;
import com.hanghae99.agitclone.user.entity.Users;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class AgitMapper {
    public Agit toEntity(AgitRequestDto agitRequestDto){
        return Agit.builder()
                .agitName(agitRequestDto.getAgitname())
                .agitInfo(agitRequestDto.getAgitinfo())
                .build();
    }

    public AgitMember toEntity(Long userId, Long agitId){
        return AgitMember.builder()
                .userId(userId)
                .agitId(agitId)
                .build();
    }

    public AgitResponseDto toAgitResponseDto(Agit agit) {
        return AgitResponseDto.builder()
                .id(agit.getId())
                .agitname(agit.getAgitName())
                .build();
    }

}

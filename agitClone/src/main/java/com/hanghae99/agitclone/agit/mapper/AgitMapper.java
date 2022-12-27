package com.hanghae99.agitclone.agit.mapper;

import com.hanghae99.agitclone.agit.dto.AgitMemberResponseDto;
import com.hanghae99.agitclone.agit.dto.AgitRequestDto;
import com.hanghae99.agitclone.agit.dto.AgitListResponseDto;
import com.hanghae99.agitclone.agit.entity.Agit;
import com.hanghae99.agitclone.agit.entity.AgitMember;
import com.hanghae99.agitclone.user.entity.Users;
import org.springframework.stereotype.Component;

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

    public AgitListResponseDto toAgitResponseDto(Agit agit) {
        return AgitListResponseDto.builder()
                .id(agit.getId())
                .agitname(agit.getAgitName())
                .build();
    }

    public AgitMemberResponseDto toAgitMemberResponseDto(Users users) {
        return AgitMemberResponseDto.builder()
                .username(users.getUsername())
                .nickname(users.getNickname())
                .build();
    }

}

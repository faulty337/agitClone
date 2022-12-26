package com.hanghae99.agitclone.agit.service;

import com.hanghae99.agitclone.agit.dto.AgitInviteRequestDto;
import com.hanghae99.agitclone.agit.dto.AgitRequestDto;
import com.hanghae99.agitclone.agit.entity.Agit;
import com.hanghae99.agitclone.agit.entity.AgitMember;
import com.hanghae99.agitclone.agit.mapper.AgitMapper;
import com.hanghae99.agitclone.agit.repository.AgitMemberRepository;
import com.hanghae99.agitclone.agit.repository.AgitRepository;
import com.hanghae99.agitclone.common.exception.CustomException;
import com.hanghae99.agitclone.user.entity.Users;
import com.hanghae99.agitclone.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.hanghae99.agitclone.common.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class AgitService {

    private final AgitRepository agitRepository;
    private final AgitMemberRepository agitMemberRepository;
    private final AgitMapper agitMapper;
    private final UserRepository userRepository;
    //아지트 생성
    @Transactional
    public void createAgit(AgitRequestDto agitRequestDto, Long userId) {
        //받은 아지트 이름으로 아지트 생성
        Agit agit = agitRepository.save(agitMapper.toEntity(agitRequestDto));
        //아지트 처음 생성시 아지트를 만든 사람은 자동으로 AgitMember에 들어가야 한다.
        AgitMember agitMember = agitMemberRepository.save(agitMapper.toEntity(userId, agit.getId()));
    }

    @Transactional
    public void inviteAgit(Long agitId, AgitInviteRequestDto agitInviteRequestDto, Long userId) {
        //실존 유저인지 확인 (Request로 받아온 username사용해서 확인)
        Users userinfo = userRepository.findByUsername(agitInviteRequestDto.getUsername()).orElseThrow(
                () -> new CustomException(USERNAME_NOT_FOUND)
        );
        //아지트 존재하는지 확인
        Agit agit = agitRepository.findById(agitId).orElseThrow(
                () -> new CustomException(AGIT_NOT_FOUND)
        );

        //아지트에 이미 초대된 상태인지 확인.(중복체크) 아니면 멤버에 추가.
        if(agitMemberRepository.findById(userinfo.getId()).isPresent()){
            if(!agitMemberRepository.findById(userinfo.getId()).equals(userinfo.getId())){
                throw new CustomException(DUPLICATE_MEMBERNAME);
            }
        }
        //아지트에 추가.
        agitMemberRepository.save(agitMapper.toEntity(userId, agitId));

    }
}

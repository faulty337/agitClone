package com.hanghae99.agitclone.agit.service;

import com.hanghae99.agitclone.agit.dto.AgitInviteRequestDto;
import com.hanghae99.agitclone.agit.dto.AgitListResponseDto;
import com.hanghae99.agitclone.agit.dto.AgitMemberResponseDto;
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

import java.util.ArrayList;
import java.util.List;

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

        Long joinTarget = userinfo.getId();

        //아지트 존재하는지 확인
        Agit agit = agitRepository.findById(agitId).orElseThrow(
                () -> new CustomException(AGIT_NOT_FOUND)
        );

        if(agit.getAgitMemberList().stream().anyMatch(agitMember -> agitMember.getUserId().equals(joinTarget))){
            throw new CustomException(DUPLICATE_MEMBERNAME);
        }

        agitMemberRepository.save(agitMapper.toEntity(userinfo.getId(), agitId));

    }

    @Transactional
    public List<AgitListResponseDto> getAgitList(Long userId) {

        //해당 userId가 들어간 모든 아지트를 검색해서 리스트에 넣어야된다.
        //userId로 agitMemberRepository에 접근해서 해당 유저가 가입한 아지트 정보 조회
        List<AgitMember> agitMemberList = agitMemberRepository.findAllByUserId(userId);
        List<Long> agitIdList = new ArrayList<>();
        for(AgitMember agitMember : agitMemberList){
            agitIdList.add(agitMember.getAgitId());
        }

        List<Agit> agitList = new ArrayList<>();
        for(Long agitId : agitIdList){
            agitList.add(agitRepository.findById(agitId).get());
        }

        List<AgitListResponseDto> agitResponseDtos = new ArrayList<>();

        for(Agit agit : agitList){
            agitResponseDtos.add(agitMapper.toAgitResponseDto(agit));
        }
        return agitResponseDtos;
    }
    //아지트에 가입한 회원 조회
    public List<AgitMemberResponseDto> getMember(Long agitId) {
        List<AgitMember> agitMemberList = agitMemberRepository.findAllByAgitId(agitId);
        List<Long> userIdList = new ArrayList<>();
        for(AgitMember agitMember : agitMemberList){
            userIdList.add(agitMember.getUserId());
        }

        List<Users> usersList = new ArrayList<>();
        for(Long userId : userIdList){
            usersList.add(userRepository.findById(userId).get());
        }

        List<AgitMemberResponseDto> agitMemberResponseDtos = new ArrayList<>();
        for(Users users : usersList){
            agitMemberResponseDtos.add(agitMapper.toAgitMemberResponseDto(users));
        }
        return agitMemberResponseDtos;
    }

}

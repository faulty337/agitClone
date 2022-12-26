package com.hanghae99.agitclone.agit.service;

import com.hanghae99.agitclone.agit.dto.AgitRequestDto;
import com.hanghae99.agitclone.agit.entity.Agit;
import com.hanghae99.agitclone.agit.mapper.AgitMapper;
import com.hanghae99.agitclone.agit.repository.AgitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgitService {

    private final AgitRepository agitRepository;
    private final AgitMapper agitMapper;
    public void createAgit(AgitRequestDto agitRequestDto) {
        Agit agit = agitRepository.save(agitMapper.toEntity(agitRequestDto));
    }
}

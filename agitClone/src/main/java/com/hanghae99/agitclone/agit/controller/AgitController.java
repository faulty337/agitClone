package com.hanghae99.agitclone.agit.controller;

import com.hanghae99.agitclone.agit.dto.AgitInviteRequestDto;
import com.hanghae99.agitclone.agit.dto.AgitRequestDto;
import com.hanghae99.agitclone.agit.dto.AgitResponseDto;
import com.hanghae99.agitclone.agit.service.AgitService;
import com.hanghae99.agitclone.common.ResponseMessage;
import com.hanghae99.agitclone.post.dto.RequestPostDto;
import com.hanghae99.agitclone.post.dto.ResponsePostDto;
import com.hanghae99.agitclone.post.service.PostService;
import com.hanghae99.agitclone.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AgitController {
    private final AgitService agitService;

    //아지트 생성
    @PostMapping("/agit")
    public ResponseEntity<ResponseMessage> createAgit(@RequestBody AgitRequestDto agitRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        agitService.createAgit(agitRequestDto, userDetails.getUserId());
        ResponseMessage<?> responseMessage = new ResponseMessage("Success", 200, null);
        return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatusCode()));
    }

    //아지트에 회원들 초대
    @PostMapping("/agit/{agitId}/join")
    public ResponseEntity<ResponseMessage> inviteAgit(@PathVariable Long agitId, @RequestBody AgitInviteRequestDto agitInviteRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        agitService.inviteAgit(agitId, agitInviteRequestDto, userDetails.getUserId());
        ResponseMessage<?> responseMessage = new ResponseMessage("Success", 200, null);
        return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatusCode()));
    }

    @GetMapping("/agit")
    public ResponseEntity<ResponseMessage> getAgit(@AuthenticationPrincipal UserDetailsImpl userDetails){
        Long userId = userDetails.getUserId();
        List<AgitResponseDto> AgitResponseDto = agitService.getAgitList(userId);
        ResponseMessage<List<AgitResponseDto>> responseMessage = new ResponseMessage("Success", 200, AgitResponseDto);
        return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatusCode()));
    }

}

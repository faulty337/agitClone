package com.hanghae99.agitclone.post.controller;

import com.hanghae99.agitclone.common.ResponseMessage;
import com.hanghae99.agitclone.post.dto.RequestPostDto;
import com.hanghae99.agitclone.post.dto.ResponsePostDto;
import com.hanghae99.agitclone.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    //게시글 등록
    //유저 정보 수정 필요
    @PostMapping("")
    public ResponseEntity<ResponseMessage> createPost(@RequestBody RequestPostDto requestPostDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        Long userId = 1L;   //내일 UserDetailsImpl로 변경 예정.
        ResponsePostDto responsePostDto = postService.createPost(requestPostDto, userDetails.getUsers);
        ResponseMessage<ResponsePostDto> responseMessage = new ResponseMessage<>("생성 성공", 200, responsePostDto);
        return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatusCode()));
    }

}

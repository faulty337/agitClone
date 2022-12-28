package com.hanghae99.agitclone.post.controller;

import com.hanghae99.agitclone.common.ResponseMessage;
import com.hanghae99.agitclone.post.dto.MainResponseDto;
import com.hanghae99.agitclone.post.dto.PostRequestDto;
import com.hanghae99.agitclone.post.dto.PostResponseDto;
import com.hanghae99.agitclone.post.service.PostService;
import com.hanghae99.agitclone.security.UserDetailsImpl;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    //게시글 등록
    //유저 정보 수정 필요

    @ApiOperation(value = "게시물 작성")
    @PostMapping("/agit/{agitId}/post")
    public ResponseEntity<ResponseMessage> createPost(
            @PathVariable Long agitId,
            @RequestBody PostRequestDto requestPostDto,
            @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails){
        PostResponseDto responsePostDto = postService.createPost(agitId, requestPostDto, userDetails.getUser());
        ResponseMessage<PostResponseDto> responseMessage = new ResponseMessage<>("Success", 200, responsePostDto);
        return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatusCode()));
    }


    @ApiOperation(value = "게시물 조회")
    @GetMapping("/agit/{agitId}")
    public ResponseEntity<ResponseMessage> getPost(
            @PathVariable Long agitId,
            @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails){
        Long userId = userDetails.getUserId();
        MainResponseDto mainResponseDto = postService.getPostList(agitId, userId);
        ResponseMessage<MainResponseDto> responseMessage = new ResponseMessage<>("Success", 200, mainResponseDto);

        return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatusCode()));
    }

    @ApiOperation(value = "게시물 수정")
    @PutMapping("/agit/post/{postId}")
    public ResponseEntity<ResponseMessage> updatePost(
            @PathVariable Long postId,
            @RequestBody PostRequestDto requestPostDto,
            @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        PostResponseDto responsePostDto = postService.updatePost(postId, requestPostDto, userDetails.getUserId());
        ResponseMessage<PostResponseDto> responseMessage = new ResponseMessage<>("Success", 200, responsePostDto);
        return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatusCode()));
    }

    @ApiOperation(value = "게시물 삭제")
    @DeleteMapping("/agit/post/{postId}")
    public ResponseEntity<ResponseMessage> deletePost(
            @PathVariable Long postId,
            @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails){
        postService.deletePost(postId, userDetails.getUserId());
        ResponseMessage<?> responseMessage = new ResponseMessage("Success", 200, null);
        return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatusCode()));
    }

}

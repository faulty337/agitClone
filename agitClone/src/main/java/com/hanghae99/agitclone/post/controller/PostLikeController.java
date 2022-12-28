package com.hanghae99.agitclone.post.controller;

import com.hanghae99.agitclone.comment.dto.CommentResponseDto;
import com.hanghae99.agitclone.common.ResponseMessage;
import com.hanghae99.agitclone.post.dto.PostHateDto;
import com.hanghae99.agitclone.post.dto.PostLikeDto;
import com.hanghae99.agitclone.post.dto.PostLikeResponseDto;
import com.hanghae99.agitclone.post.service.PostLikeService;
import com.hanghae99.agitclone.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post/{postId}")
@RequiredArgsConstructor
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping("/like")
    public ResponseEntity<ResponseMessage> updateLikeState(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long postId, @RequestBody PostLikeDto postLike){
        Long userId = userDetails.getUserId();
//        boolean isHate = postLike.isLikeState();

        PostLikeResponseDto likeResponseDto = postLikeService.updateLikeState(userId, postId);

        ResponseMessage<PostLikeResponseDto> responseMessage = new ResponseMessage<>("Success", 200, likeResponseDto);
        return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatusCode()));

//        if(result){
//            ResponseMessage<PostLikeDto> responseMessage = new ResponseMessage<>("Success", 200, new PostLikeDto(true));
//            return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatusCode()));
//        }
//        ResponseMessage<PostLikeDto> responseMessage = new ResponseMessage<>("Success", 200, new PostLikeDto(false));
//        return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatusCode()));
    }

    @PostMapping("/hate")
    public ResponseEntity<ResponseMessage> updateHateState(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long postId){
        Long userId = userDetails.getUserId();

        PostLikeResponseDto likeResponseDto = postLikeService.updateHateState(userId, postId);

        ResponseMessage<PostLikeResponseDto> responseMessage = new ResponseMessage<>("Success", 200, likeResponseDto);
        return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatusCode()));


//        boolean isHate = postHate.isHateState();
//
//        boolean result = postLikeService.updateHateState(userId, postId, postHate.isHateState(), isHate);
//
//        if(result){
//            ResponseMessage<PostHateDto> responseMessage = new ResponseMessage<>("Success", 200, new PostHateDto(true));
//            return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatusCode()));
//        }
//        ResponseMessage<PostHateDto> responseMessage = new ResponseMessage<>("Success", 200, new PostHateDto(false));
//        return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatusCode()));
    }

}

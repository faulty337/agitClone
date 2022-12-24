package com.hanghae99.agitclone.comment.controller;


import com.hanghae99.agitclone.comment.dto.CommentRequestDto;
import com.hanghae99.agitclone.comment.service.CommentService;
import com.hanghae99.agitclone.common.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
//@RequestMapping("/post/{postId}/comment")
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping()
    public ResponseEntity<ResponseMessage> createComment(@RequestBody CommentRequestDto requestDto){

        commentService.createComment(requestDto);

        ResponseMessage<?> responseMessage = new ResponseMessage<>("댓글작성 성공", 200, null);

        return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatusCode()));
    }
}

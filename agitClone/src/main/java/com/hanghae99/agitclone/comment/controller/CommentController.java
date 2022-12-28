package com.hanghae99.agitclone.comment.controller;


import com.hanghae99.agitclone.comment.dto.CommentRequestDto;
import com.hanghae99.agitclone.comment.dto.CommentResponseDto;
import com.hanghae99.agitclone.comment.service.CommentService;
import com.hanghae99.agitclone.common.ResponseMessage;
import com.hanghae99.agitclone.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post/{postId}/comment")
public class CommentController {

    private final CommentService commentService;

    @ApiOperation(value = "댓글 작성")
    @PostMapping
    public ResponseEntity<ResponseMessage> createComment(
            @ApiIgnore @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody CommentRequestDto requestDto,
            @PathVariable Long postId){

        CommentResponseDto commentResponseDto = commentService.createComment(requestDto, userDetails.getUser(), postId);

        ResponseMessage<CommentResponseDto> responseMessage = new ResponseMessage<>("Success", 200, commentResponseDto);

        return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatusCode()));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<ResponseMessage> updateComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CommentRequestDto requestDto,
                                                         @PathVariable Long commentId, @PathVariable Long postId) {
        Long userId = userDetails.getUser().getId();

        CommentResponseDto commentResponseDto = commentService.updateComment(requestDto, commentId, postId, userId);

        ResponseMessage<CommentResponseDto> responseMessage = new ResponseMessage<>("Success", 200, commentResponseDto);

        return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatusCode()));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ResponseMessage> deleteComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long commentId, @PathVariable Long postId) {

        Long userId = userDetails.getUser().getId();

        commentService.deleteComment(commentId, userId, postId);

        ResponseMessage<CommentResponseDto> responseMessage = new ResponseMessage<>("Success", 200, null);

        return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(responseMessage.getStatusCode()));
    }
}

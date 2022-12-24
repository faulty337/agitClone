package com.hanghae99.agitclone.comment.service;

import com.hanghae99.agitclone.comment.dto.CommentRequestDto;
import com.hanghae99.agitclone.comment.dto.CommentResponseDto;
import com.hanghae99.agitclone.comment.entity.Comment;
import com.hanghae99.agitclone.comment.mapper.CommentMapper;
import com.hanghae99.agitclone.comment.repository.CommentRepository;
import com.hanghae99.agitclone.common.exception.CustomException;
import com.hanghae99.agitclone.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;

    //댓글 등록
    @Transactional
    public CommentResponseDto createComment(CommentRequestDto requestDto){

        Comment comment = commentMapper.toComment(requestDto);

        commentRepository.save(comment);

        return commentMapper.toResponse(comment);
    }

    //댓글 수정
    @Transactional
    public CommentResponseDto updateComment(CommentRequestDto requestDto, long commentId) {


        //댓글 존재 확인
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new CustomException(ErrorCode.COMMENT_NOT_FOUND)
        );

        comment.update(requestDto.getContent());
        //게시글 수정
        return commentMapper.toResponse(comment);

    }

    //댓글 삭제
    public void deleteComment(long commentId) {
        //댓글 존재 확인
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new CustomException(ErrorCode.COMMENT_NOT_FOUND)
        );

        commentRepository.deleteById(commentId);
    }
}

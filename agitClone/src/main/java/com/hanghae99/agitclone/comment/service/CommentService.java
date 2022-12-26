package com.hanghae99.agitclone.comment.service;

import com.hanghae99.agitclone.comment.dto.CommentRequestDto;
import com.hanghae99.agitclone.comment.dto.CommentResponseDto;
import com.hanghae99.agitclone.comment.entity.Comment;
import com.hanghae99.agitclone.comment.mapper.CommentMapper;
import com.hanghae99.agitclone.comment.repository.CommentRepository;
import com.hanghae99.agitclone.common.exception.CustomException;
import com.hanghae99.agitclone.common.exception.ErrorCode;
import com.hanghae99.agitclone.post.repository.PostRepository;
import com.hanghae99.agitclone.user.entity.Users;
import com.hanghae99.agitclone.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    //댓글 등록
    @Transactional
    public CommentResponseDto createComment(CommentRequestDto requestDto, Long userId, Long postId){

        Users users = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );

        postRepository.findById(postId).orElseThrow(() -> new CustomException(ErrorCode.CONTENT_NOT_FOUND));

        Comment comment = commentMapper.toComment(requestDto,users);

        commentRepository.save(comment);

        return commentMapper.toResponse(comment);
    }

    //댓글 수정
    @Transactional
    public CommentResponseDto updateComment(CommentRequestDto requestDto, Long commentId, Long userId, Long postId) {

        Users users = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );

        postRepository.findById(postId).orElseThrow(() -> new CustomException(ErrorCode.CONTENT_NOT_FOUND));

        //댓글 존재 확인
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new CustomException(ErrorCode.COMMENT_NOT_FOUND)
        );

        if(!comment.getUsers().getUsername().equals(users.getUsername())){
            throw new CustomException(ErrorCode.AUTHORIZATION_UPDATE_FAIL);
        }

        comment.update(requestDto.getContent());
        //게시글 수정
        return commentMapper.toResponse(comment);

    }

    //댓글 삭제
    public void deleteComment(Long commentId, Long userId, Long postId) {

        Users users = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );

        postRepository.findById(postId).orElseThrow(() -> new CustomException(ErrorCode.CONTENT_NOT_FOUND));

        //댓글 존재 확인
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new CustomException(ErrorCode.COMMENT_NOT_FOUND)
        );

        if(!comment.getUsers().getUsername().equals(users.getUsername())){
            throw new CustomException(ErrorCode.AUTHORIZATION_DELETE_FAIL);
        }

        commentRepository.deleteById(commentId);
    }
}

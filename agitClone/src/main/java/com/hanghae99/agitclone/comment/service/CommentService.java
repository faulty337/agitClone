package com.hanghae99.agitclone.comment.service;

import com.hanghae99.agitclone.comment.dto.CommentRequestDto;
import com.hanghae99.agitclone.comment.dto.CommentResponseDto;
import com.hanghae99.agitclone.comment.entity.Comment;
import com.hanghae99.agitclone.comment.mapper.CommentMapper;
import com.hanghae99.agitclone.comment.repository.CommentRepository;
import com.hanghae99.agitclone.common.exception.CustomException;
import com.hanghae99.agitclone.common.exception.ErrorCode;
import com.hanghae99.agitclone.post.entity.Post;
import com.hanghae99.agitclone.post.repository.PostRepository;
import com.hanghae99.agitclone.user.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;

    private final PostRepository postRepository;

    //댓글 등록
    @Transactional
    public CommentResponseDto createComment(CommentRequestDto requestDto, Users users, Long postId) {

        Post post = postRepository.findById(postId).orElseThrow(() -> new CustomException(ErrorCode.CONTENT_NOT_FOUND));

        Comment comment = commentRepository.save(commentMapper.toComment(requestDto, users));

        post.addCommentList(comment);

        return commentMapper.toResponse(comment);
    }

    //댓글 수정

    @Transactional
    public CommentResponseDto updateComment(CommentRequestDto requestDto, Long commentId, Long postId, Long userId) {

        Post post = postRepository.findById(postId).orElseThrow(() -> new CustomException(ErrorCode.CONTENT_NOT_FOUND));
        System.out.println("CommentService.updateComment");
        System.out.println(commentId + " " + postId + " " + userId);
        for(Comment c : post.getCommentList()){
            System.out.println(c.getId() + " " + commentId);
        }
        if(post.getCommentList().stream().noneMatch(comment -> comment.getId().equals(commentId))){
            throw new CustomException(ErrorCode.CONTENT_NOT_FOUND);
        }
        //댓글 존재 확인
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new CustomException(ErrorCode.COMMENT_NOT_FOUND)
        );


        if (!comment.getUsers().getId().equals(userId)) {
            throw new CustomException(ErrorCode.AUTHORIZATION_UPDATE_FAIL);
        }

        comment.update(requestDto.getContent());
        //댓글 수정
        return commentMapper.toResponse(comment);

    }

    //댓글 삭제
    public void deleteComment(Long commentId, Long userId, Long postId) {

        Post post = postRepository.findById(postId).orElseThrow(() -> new CustomException(ErrorCode.CONTENT_NOT_FOUND));

        if(post.getCommentList().stream().noneMatch(comment -> comment.getId().equals(commentId))){
            throw new CustomException(ErrorCode.CONTENT_NOT_FOUND);
        }
        //댓글 존재 확인
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new CustomException(ErrorCode.COMMENT_NOT_FOUND)
        );

        if (!comment.getUsers().getId().equals(userId)) {
            throw new CustomException(ErrorCode.AUTHORIZATION_DELETE_FAIL);
        }

        commentRepository.deleteById(commentId);
    }
}

package com.hanghae99.agitclone.post.service;

import com.hanghae99.agitclone.common.exception.CustomException;
import com.hanghae99.agitclone.common.exception.ErrorCode;
import com.hanghae99.agitclone.post.dto.RequestPostDto;
import com.hanghae99.agitclone.post.dto.ResponsePostDto;
import com.hanghae99.agitclone.post.entity.Post;
import com.hanghae99.agitclone.post.mapper.PostMapper;
import com.hanghae99.agitclone.post.repository.PostRepository;
import com.hanghae99.agitclone.user.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    //게시글 등록
    @Transactional
    public ResponsePostDto createPost(RequestPostDto requestPostDto, Users users) {
        //게시글 저장
        Post post = postMapper.toEntity(requestPostDto, users);
        postRepository.save(post);

        return postMapper.toResponsePostDto(post);
    }

    //게시글 수정
    @Transactional
    public ResponsePostDto updatePost(Long postId, RequestPostDto requestPostDto, Users users){
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new CustomException(ErrorCode.CONTENT_NOT_FOUND)
        );

        //게시글 작성자와 멤버 정보가 일치하지 않는 경우
        if(!post.getUser().getId().equals(users.getId())){
            throw new CustomException(ErrorCode.AUTHORIZATION_UPDATE_FAIL);
        }

        post.update(requestPostDto.getContent());

        return postMapper.toResponsePostDto(post);
    }
}

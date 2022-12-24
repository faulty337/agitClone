package com.hanghae99.agitclone.post.service;

import com.hanghae99.agitclone.agit.entity.Agit;
import com.hanghae99.agitclone.agit.repository.AgitRepository;
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

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final AgitRepository agitRepository;
    private final PostMapper postMapper;

    public List<ResponsePostDto> getPostList(Long agitId, Users users) {
        Agit agit = agitRepository.findById(agitId).orElseThrow(
                ()->new CustomException(ErrorCode.AGIT_NOT_FOUND)
        );
        if(agit.getAgitMemberList().stream().noneMatch(agitMember -> agitMember.getUserId().equals(users.getId()))){
            throw new CustomException(ErrorCode.AUTHORIZATION_AGIT_FAIL);
        }

        List<ResponsePostDto> postDtoList = new ArrayList<>();
        List<Post> postList = agit.getPostList();

        for(Post post : postList){
            postDtoList.add(postMapper.toResponsePostDto(post, users));
        }
        return postDtoList;
    }

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
    public ResponsePostDto updatePost(Long postId, RequestPostDto requestPostDto){
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new CustomException(ErrorCode.CONTENT_NOT_FOUND)
        );
        post.update(requestPostDto.getContent());

        return postMapper.toResponsePostDto(post);
    }
}

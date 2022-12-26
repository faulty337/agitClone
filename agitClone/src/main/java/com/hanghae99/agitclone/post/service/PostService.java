package com.hanghae99.agitclone.post.service;

import com.hanghae99.agitclone.agit.entity.Agit;
import com.hanghae99.agitclone.agit.repository.AgitRepository;
import com.hanghae99.agitclone.comment.entity.Comment;
import com.hanghae99.agitclone.comment.repository.CommentRepository;
import com.hanghae99.agitclone.common.exception.CustomException;
import com.hanghae99.agitclone.common.exception.ErrorCode;
import com.hanghae99.agitclone.post.dto.RequestPostDto;
import com.hanghae99.agitclone.post.dto.ResponsePostDto;
import com.hanghae99.agitclone.post.entity.Post;
import com.hanghae99.agitclone.post.entity.PostLike;
import com.hanghae99.agitclone.post.mapper.PostMapper;
import com.hanghae99.agitclone.post.repository.PostLikeRepository;
import com.hanghae99.agitclone.post.repository.PostRepository;
import com.hanghae99.agitclone.user.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.hanghae99.agitclone.common.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final AgitRepository agitRepository;
    private final PostMapper postMapper;
    private final CommentRepository commentRepository;
    private final PostLikeRepository postLikeRepository;

    public List<ResponsePostDto> getPostList(Long agitId, Long userId) {
        Agit agit = agitRepository.findById(agitId).orElseThrow(
                ()->new CustomException(ErrorCode.AGIT_NOT_FOUND)
        );
        if(agit.getAgitMemberList().stream().noneMatch(agitMember -> agitMember.getUserId().equals(userId))){
            throw new CustomException(ErrorCode.AUTHORIZATION_AGIT_FAIL);
        }

        List<ResponsePostDto> postDtoList = new ArrayList<>();
        List<Post> postList = agit.getPostList();

        for(Post post : postList){
            postDtoList.add(postMapper.toResponsePostDto(post, userId));
        }
        return postDtoList;
    }

    //게시글 등록
    @Transactional
    public ResponsePostDto createPost(Long agitId, RequestPostDto requestPostDto, Users users) {
        //게시글 저장
        Post post = postRepository.save(postMapper.toEntity(requestPostDto, users, agitId));

        //아지트에 게시글 정보 추가
        Agit agit = agitRepository.findById(agitId).orElseThrow(
                () -> new CustomException(AGIT_NOT_FOUND)
        );
        agit.addPostList(post);
        return postMapper.toResponsePostDto(post);
    }

    //게시글 수정
    @Transactional
    public ResponsePostDto updatePost(Long postId, RequestPostDto requestPostDto, Users users){
        //게시글 존재 확인.
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new CustomException(CONTENT_NOT_FOUND)
        );

        //아지트 확인
        Agit agit = agitRepository.findById(post.getAgitId()).orElseThrow(
                () -> new CustomException(AGIT_NOT_FOUND)
        );
        //게시글 작성자와 현재 유저가 같은 사람인지 확인.
        if(!post.getUser().getId().equals(users.getId())){
            throw new CustomException(AUTHORIZATION_UPDATE_FAIL);
        }

        post.update(requestPostDto.getContent());
        return postMapper.toResponsePostDto(post);
    }

    //게시글 삭제
    @Transactional
    public void deletePost(Long postId, Users users){
        //게시글 존재 확인.
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new CustomException(CONTENT_NOT_FOUND)
        );
        //게시글 작성자와 현재 유저가 같은 사람인지 확인.
        if(!post.getUser().getId().equals(users.getId())){
            throw new CustomException(AUTHORIZATION_UPDATE_FAIL);
        }

        //아지트에서 제거 -> 영속성을 사용한 제거? 마지막 delete(post) 사용시 아지트의 postlist에서 삭제된다.
        Agit agit = agitRepository.findById(post.getAgitId()).orElseThrow(
                () -> new CustomException(AGIT_NOT_FOUND)
        );


        //게시글에 달려있는 댓글 정보 삭제
        List<Long> commentList = new ArrayList<>();
        for(Comment comment : post.getCommentList()){
            commentList.add(comment.getId());
        }

        if(!commentList.isEmpty()){
            commentRepository.deleteAllByIdInQuery(commentList);
        }

        //게시글 관련된 좋아요 정보 삭제
        List<Long> postLikeList = new ArrayList<>();
        for(PostLike postLike : post.getPostLikeList()){
            postLikeList.add(postLike.getId());
        }

        if(!postLikeList.isEmpty()){
            postLikeRepository.deleteAllByIdInQuery(postLikeList);
        }

        postRepository.delete(post);
    }
}

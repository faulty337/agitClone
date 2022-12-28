package com.hanghae99.agitclone.post.service;


import com.hanghae99.agitclone.common.exception.CustomException;
import com.hanghae99.agitclone.common.exception.ErrorCode;
import com.hanghae99.agitclone.post.dto.PostLikeResponseDto;
import com.hanghae99.agitclone.post.entity.Post;
import com.hanghae99.agitclone.post.entity.PostLike;
import com.hanghae99.agitclone.post.mapper.PostLikeMapper;
import com.hanghae99.agitclone.post.repository.PostLikeRepository;
import com.hanghae99.agitclone.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final PostLikeMapper postLikeMapper;


    // isHate = true 일경우 싫어요
    // isHate = false 일경우 좋아요
    // istHate = null 일경우 0

    //1. 현재 상태 확인
    //postLike 에서 데이터가 존재하지 않으면 null
    //postLike 에서 데이터가 존재하면서 isHate가 False면 true
    //postLike 에서 데이터가 존재하면서 isHate가 True면 false
    //2. 상태에 따른 데이터 변환
    //like => true or null
    //true -> like : null, false -> save or isHate
    //null -> true = delete()

    //hate => false or null

    @Transactional
    public PostLikeResponseDto updateLikeState(Long userId, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new CustomException(ErrorCode.CONTENT_NOT_FOUND)
        );
        PostLikeResponseDto likeResponseDto = new PostLikeResponseDto();


        PostLike postLike = postLikeRepository.findByPostIdAndUserId(postId, userId).orElse(null);
        //데이터가 존재 하거나, null

        if(postLike == null){
            postLike = new PostLike(userId, postId, false);
            postLikeRepository.save(postLike);
            post.updateLikeCount(true);
            likeResponseDto.setPostLike(true);
            likeResponseDto.setLikeCount(post.getLikeCount());
        }else{
            if(postLike.getIsHate()){
                postLike.updateIsHate(false);
                post.updateLikeCount(true);
                post.updateHateCount(false);
                likeResponseDto.setPostLike(true);
                likeResponseDto.setLikeCount(post.getLikeCount());
            }else{
                postLikeRepository.delete(postLike);
                post.updateLikeCount(false);
                likeResponseDto.setPostLike(null);
                likeResponseDto.setLikeCount(post.getLikeCount());
            }
        }

        return likeResponseDto;
        //---------------------

//
//
//
//        Optional<PostLike> postLike = postLikeRepository.findByPostIdAndUserId(postId, userId, isHate);
//        //좋아요와 싫어요 부분이 겹치는게 아닌가 ..?
//        long changeLikeCount;
//        long changeHateCount;
//        long thumbCount;
//
//        if (likeState && postLike.isPresent() && !isHate) {
//
//            changeLikeCount = post.getLikeCount() - 1;
//            post.updateLikeCount(changeLikeCount);
//
//            postLikeRepository.deleteLikeByPostIdAndUserId(postId, userId, false);
//
//            return false;
//        }
//
//        if (likeState) {
//            return false;
//        }
//
//        if (postLike.isPresent()) {
//            throw new CustomException(ErrorCode.DUPLICATION_COMMENT_LIKE_EXCEPTION_MSG);
//        }
//
//        //싫어요 한 상태일 경우 -> 싫어요를 취소하고 좋아요를 카운트
//        if(isHate){//? 그러면 값을 어디에 넣어야 하는가..?
//
//            changeLikeCount = post.getLikeCount() + 1;
//            changeHateCount = post.getHateCount() - 1;
//
//            post.updateLikeCount(changeLikeCount);
//            post.updateHateCount(changeHateCount);
//
//            PostLike newPostLike = postLikeMapper.toPostLike(userId, postId, isHate);
//            postLikeRepository.save(newPostLike);
//
//            PostLike newPostHate = postLikeMapper.toPostLike(userId, postId, isHate);
//            postLikeRepository.save(newPostHate);
//
//            return true;
//
//        }
//
//        changeLikeCount = post.getLikeCount() + 1;
//        post.updateLikeCount(changeLikeCount);
//
//        PostLike newPostLike = postLikeMapper.toPostLike(userId, postId, isHate);
//        postLikeRepository.save(newPostLike);
//
//        return true;

    }



    //1. 현재 상태 확인
    //postLike 에서 데이터가 존재하지 않으면 null
    //postLike 에서 데이터가 존재하면서 isHate가 False면 true
    //postLike 에서 데이터가 존재하면서 isHate가 True면 false

    //2. 상태에 따른 데이터 변환
    //like => false or null
    //false -> like : null, true -> save or isHate
    //null -> false = delete()

    //hate => false or null


    @Transactional
    public PostLikeResponseDto updateHateState(Long userId, Long postId/*, boolean hateState, boolean isHate*/) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new CustomException(ErrorCode.CONTENT_NOT_FOUND)
        );
        PostLikeResponseDto likeResponseDto = new PostLikeResponseDto();

        PostLike postLike = postLikeRepository.findByPostIdAndUserId(postId, userId).orElse(null);
        //데이터가 존재 하거나, null

        if(postLike == null){
            postLike = new PostLike(userId, postId, true);
            postLikeRepository.save(postLike);
            post.updateHateCount(true);
            likeResponseDto.setPostLike(false);
            likeResponseDto.setHateCount(post.getHateCount());
        }else{
            if(postLike.getIsHate()){
                postLikeRepository.delete(postLike);
                post.updateHateCount(false);
                likeResponseDto.setPostLike(null);
                likeResponseDto.setHateCount(post.getHateCount());
            }else{
                postLike.updateIsHate(true);
                post.updateLikeCount(false);
                post.updateHateCount(true);
                likeResponseDto.setPostLike(false);
                likeResponseDto.setHateCount(post.getHateCount());
            }
        }

        return likeResponseDto;


//        Post post = postRepository.findById(postId).orElseThrow(
//                () -> new CustomException(ErrorCode.CONTENT_NOT_FOUND)
//        );
//
//        Optional<PostLike> postLike = postLikeRepository.findByPostIdAndUserId(postId, userId, isHate);
//
//        long changeHateCount;
//
//        if (hateState && postLike.isPresent() && isHate) {
//
//            changeHateCount = post.getHateCount() - 1;
//            post.updateHateCount(changeHateCount);
//
//            postLikeRepository.deleteHateByPostIdAndUserId(postId, userId, true);
//
//            return false;
//        }
//
//        if (hateState) {
//            return false;
//        }
//
//        if (postLike.isPresent()) {
//            throw new CustomException(ErrorCode.DUPLICATION_COMMENT_HATE_EXCEPTION_MSG);
//        }
//
//        //좋아요 한 상태일 경우 -> 좋아요를 취소하고 싫어요를 카운트
//
//        changeHateCount = post.getHateCount() + 1;
//        post.updateHateCount(changeHateCount);
//
//        PostLike newPostHate = postLikeMapper.toPostLike(userId, postId, isHate);
//        postLikeRepository.save(newPostHate);
//
//        return true;
    }
}


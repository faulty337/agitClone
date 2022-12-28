package com.hanghae99.agitclone.post.mapper;

import com.hanghae99.agitclone.comment.mapper.CommentMapper;
import com.hanghae99.agitclone.post.dto.PostRequestDto;
import com.hanghae99.agitclone.post.dto.PostResponseDto;
import com.hanghae99.agitclone.post.entity.Post;
import com.hanghae99.agitclone.post.entity.PostLike;
import com.hanghae99.agitclone.user.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
@Component
@RequiredArgsConstructor
public class PostMapper {

    private final CommentMapper commentMapper;

    //Dto -> Entity -> 받은 정보로 Post 만들어주기.
    public Post toEntity(PostRequestDto requestPostDto, Users users, long agitId) {
        return Post.builder()
                .content(requestPostDto.getContent())
                .users(users)
                .isModified(false)
                .likeCount(0)
                .hateCount(0)
                .agitId(agitId)
                .build();
    }

    //Entity -> Dto -> 받은 Post로 dto 반환하기.
    //문제1. postlike를 어떻게 반환해야 하는가..? postlike는 모르겠다. 추 후 변경한다.
    public PostResponseDto toResponsePostDto(Post post){
        return PostResponseDto.builder()
                .id(post.getId())
                .username(post.getUsers().getUsername())
                .nickname(post.getUsers().getNickname())
                .content(post.getContent())
                .likeCount(post.getLikeCount())
                .hateCount(post.getHateCount())
                .createdAt(post.getCreatedAt())
                .isModified(post.isModified())
                .commentList(post.getCommentList().stream().map(comment -> commentMapper.toResponse(comment)).collect(Collectors.toList()))
                .build();
    }
    public PostResponseDto toResponsePostDto(Post post, Long userId){
        PostLike postLike = post.getPostLikeList().stream().filter(temp -> temp.getUserId().equals(userId)).findFirst().orElse(null);
        Boolean like = postLike == null ? null : !postLike.getIsHate();
        return PostResponseDto.builder()

                .id(post.getId())
                .username(post.getUsers().getUsername())
                .nickname(post.getUsers().getNickname())
                .content(post.getContent())
                .postLike(like)
                .likeCount(post.getLikeCount())
                .hateCount(post.getHateCount())
                .createdAt(post.getCreatedAt())
                .isModified(post.isModified())
                .commentList(post.getCommentList().stream().map(comment -> commentMapper.toResponse(comment)).collect(Collectors.toList()))
                .build();
    }

    //전체 조회용 필요한가?


}

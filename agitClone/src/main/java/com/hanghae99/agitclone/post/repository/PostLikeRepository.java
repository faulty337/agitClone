package com.hanghae99.agitclone.post.repository;

import com.hanghae99.agitclone.post.entity.Post;
import com.hanghae99.agitclone.post.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    @Transactional
    @Modifying
    @Query("delete from PostLike pl where pl.id = :ids")
    void deleteAllByIdInQuery(@Param("ids") List<Long> ids);

    @Modifying
    @Query("delete from PostLike pl where pl.postId = :id")
    void deleteAllByPostId(@Param("id") Long id);

//    void deleteLikeByPostIdAndUserId(Long postId, Long userId, boolean isHate);
//
//    void deleteHateByPostIdAndUserId(Long postId, Long userId, boolean isHate);

    Optional<PostLike> findByPostIdAndUserId(Long postId, Long userId);
}

package com.hanghae99.agitclone.post.repository;

import com.hanghae99.agitclone.post.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    @Transactional
    @Modifying
    @Query("delete from PostLike pl where pl.id = :ids")
    void deleteAllByIdInQuery(@Param("ids") List<Long> ids);

}

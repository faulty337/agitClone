package com.hanghae99.agitclone.comment.repository;

import com.hanghae99.agitclone.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long>{
    @Transactional
    @Modifying
    @Query("delete from Comment c where c.id in :ids")
    void deleteAllByIdInQuery(@Param("ids") List<Long> ids);



}
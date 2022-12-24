package com.hanghae99.agitclone.comment.repository;

import com.hanghae99.agitclone.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long>{

}
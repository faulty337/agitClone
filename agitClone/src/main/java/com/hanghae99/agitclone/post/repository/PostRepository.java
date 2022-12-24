package com.hanghae99.agitclone.post.repository;

import com.hanghae99.agitclone.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.PostMapping;

public interface PostRepository extends JpaRepository <Post, Long> {

}

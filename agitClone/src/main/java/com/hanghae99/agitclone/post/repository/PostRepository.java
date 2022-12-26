package com.hanghae99.agitclone.post.repository;

import com.hanghae99.agitclone.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

public interface PostRepository extends JpaRepository <Post, Long> {



    //postlikeRepository에 넣어야 되는거
    /*@Transactional
    @Modifying
    @Query("delete from PostLike l where l.commentId in :ids")
    void deleteAllByIdQuery(@Param("ids") List<Long> ids);
*/
}

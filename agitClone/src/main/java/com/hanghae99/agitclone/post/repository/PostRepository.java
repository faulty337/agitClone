package com.hanghae99.agitclone.post.repository;

import com.hanghae99.agitclone.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository <Post, Long> {



    //postlikeRepository에 넣어야 되는거
    /*@Transactional
    @Modifying
    @Query("delete from PostLike l where l.commentId in :ids")
    void deleteAllByIdQuery(@Param("ids") List<Long> ids);
*/
}

package me.seho.beproject1st.repository;

import me.seho.beproject1st.domain.Comment;
import me.seho.beproject1st.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    Integer countByPost(Post post);
}

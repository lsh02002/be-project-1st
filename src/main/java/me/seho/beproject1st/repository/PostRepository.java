package me.seho.beproject1st.repository;

import me.seho.beproject1st.domain.Post;
import me.seho.beproject1st.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByUserEmail(String email);
    Post findByUserAndPostId(User user, Integer postId);
}

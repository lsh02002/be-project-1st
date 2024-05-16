package me.seho.beproject1st.repository;

import me.seho.beproject1st.domain.Likes;
import me.seho.beproject1st.domain.Post;
import me.seho.beproject1st.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Likes, Integer> {
    Integer countByPost(Post post);
    Integer countByPostAndUser(Post post, User user);
    void deleteByPostAndUser(Post post, User user);
}

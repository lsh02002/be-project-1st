package me.seho.beproject1st.service;

import lombok.AllArgsConstructor;
import me.seho.beproject1st.domain.Likes;
import me.seho.beproject1st.domain.Post;
import me.seho.beproject1st.domain.User;
import me.seho.beproject1st.repository.LikeRepository;
import me.seho.beproject1st.repository.PostRepository;
import me.seho.beproject1st.repository.UserRepository;
import me.seho.beproject1st.web.dto.auth.AuthInfo;
import me.seho.beproject1st.web.dto.post.PostResponse;
import me.seho.beproject1st.web.dto.post.PostsResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class LikeService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final CommentService commentService;
    public Integer getLikesCount(@PathVariable(value = "post_id") Integer postId) {
        Post post = postRepository.findById(postId).get();

        return likeRepository.countByPost(post);
    }

    @Transactional
    public Integer setLikesCount(AuthInfo authInfo, Integer postId){
        try {
            User user = userRepository.findById(authInfo.getUserId().intValue()).get();
            Post post = postRepository.findById(postId).get();

            Integer count = likeRepository.countByPostAndUser(post, user);

            if(count>0){
                likeRepository.deleteByPostAndUser(post, user);
                return likeRepository.countByPost(post);
            }

            Likes like = Likes.builder()
                    .user(user)
                    .post(post)
                    .build();

            likeRepository.save(like);
            return likeRepository.countByPost(post);

        }catch (RuntimeException e){
            e.printStackTrace();

            return 0;
        }
    }

    public PostsResponse getLikesByUserId(Integer userId){
        List<Likes> likes = likeRepository.findByUserId(userId);
        List<PostResponse> postResponses = new ArrayList<>();

        for(Likes like : likes) {
            Post post = like.getPost();
            PostResponse postResponse = PostResponse.builder()
                            .id(post.getPostId())
                            .title(post.getTitle())
                            .content(post.getContent())
                            .author(post.getUser().getEmail())
                            .create_at(post.getCreateAt().toString())
                            .modify_at(post.getModifyAt() != null ? post.getModifyAt().toString() : null)
                            .count(commentService.getCommentCountByPost(post.getPostId()))
                            .build();

            postResponses.add(postResponse);
        }

        return PostsResponse.builder()
                .posts(postResponses)
                .build();

    }
}

package me.seho.beproject1st.service;

import lombok.AllArgsConstructor;
import me.seho.beproject1st.domain.Post;
import me.seho.beproject1st.domain.User;
import me.seho.beproject1st.repository.PostRepository;
import me.seho.beproject1st.repository.UserRepository;
import me.seho.beproject1st.web.dto.auth.AuthInfo;
import me.seho.beproject1st.web.dto.post.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class PostService {

    UserRepository userRepository;
    PostRepository postRepository;
    CommentService commentService;
    public Boolean registerPost(AuthInfo authInfo, PostRequest postRequest) {
        try {
            User user = userRepository.findById(authInfo.getUserId().intValue()).get();

            Post post = Post.builder()
                    .title(postRequest.getTitle())
                    .content(postRequest.getContent())
                    .user(user)
                    .createAt(LocalDateTime.now())
                    .build();

            postRepository.save(post);

            return true;

        }catch (Exception e){
            return false;
        }
    }

    public PostsResponse getAllPost(){
        List<Post> posts = postRepository.findAll();

        List<PostResponse> postResponses = posts.stream()
                .map((post)->PostResponse.builder()
                        .id(post.getPostId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .author(post.getUser().getEmail())
                        .create_at(post.getCreateAt().toString())
                        .modify_at(post.getModifyAt() != null ? post.getModifyAt().toString() : null)
                        .count(commentService.getCommentCountByPost(post.getPostId()))
                        .build())
                .toList();

        return PostsResponse.builder()
                .posts(postResponses)
                .build();
    }

    public PostResponse getPostById(Integer postId){
        Post post = postRepository.findById(postId).get();

        return PostResponse.builder()
                .id(post.getPostId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getUser().getEmail())
                .create_at(post.getCreateAt().toString())
                .modify_at(post.getModifyAt() != null ? post.getModifyAt().toString() : null)
                .build();
    }

    public PostsResponse getPostsByUserEmail(String authEmail){
        List<Post> posts = postRepository.findByUserEmail(authEmail);
        List<PostResponse> postResponses = posts.stream()
                .map((post)->PostResponse.builder()
                        .id(post.getPostId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .author(post.getUser().getEmail())
                        .create_at(post.getCreateAt().toString())
                        .modify_at(post.getModifyAt() != null ? post.getModifyAt().toString() : null)
                        .build())
                .toList();

        return PostsResponse.builder()
                .posts(postResponses)
                .build();
    }

    public PostPutResponse modifyPost(AuthInfo authInfo, @PathVariable(value = "post_id") Integer postId, @RequestBody PostPutRequest postPutRequest){
        try {
            User user = userRepository.findById(authInfo.getUserId().intValue()).get();
            Post post = postRepository.findByUserAndPostId(user, postId);

            post.setPostId(postId);
            post.setTitle(postPutRequest.getTitle());
            post.setContent(postPutRequest.getContent());
            post.setUser(user);
            post.setModifyAt(LocalDateTime.now());

            postRepository.save(post);

            return PostPutResponse.builder()
                    .title(post.getTitle())
                    .content(post.getContent())
                    .build();
        }catch (RuntimeException e){
            e.printStackTrace();

            return null;
        }
    }
}

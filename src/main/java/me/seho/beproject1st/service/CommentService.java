package me.seho.beproject1st.service;

import lombok.AllArgsConstructor;
import me.seho.beproject1st.domain.Comment;
import me.seho.beproject1st.domain.Post;
import me.seho.beproject1st.domain.User;
import me.seho.beproject1st.repository.CommentRepository;
import me.seho.beproject1st.repository.PostRepository;
import me.seho.beproject1st.repository.UserRepository;
import me.seho.beproject1st.web.dto.ResultResponse;
import me.seho.beproject1st.web.dto.auth.AuthInfo;
import me.seho.beproject1st.web.dto.comment.CommentPutRequest;
import me.seho.beproject1st.web.dto.comment.CommentRequest;
import me.seho.beproject1st.web.dto.comment.CommentResponse;
import me.seho.beproject1st.web.dto.comment.CommentsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService {
    private UserRepository userRepository;
    private PostRepository postRepository;
    private CommentRepository commentRepository;

    public Boolean registerComment(AuthInfo authInfo, @RequestBody CommentRequest commentRequest){
        try {

            User user = userRepository.findById(authInfo.getUserId().intValue()).get();
            Post post = postRepository.findById(Integer.valueOf(commentRequest.getPost_id())).get();

            Comment comment = Comment.builder()
                    .content(commentRequest.getContent())
                    .user(user)
                    .post(post)
                    .createAt(LocalDateTime.now())
                    .build();

            commentRepository.save(comment);

            return true;
        } catch (RuntimeException e){
            e.printStackTrace();

            return false;
        }
    }

    public CommentsResponse getComments(){
        List<Comment> comments = commentRepository.findAll();

        List<CommentResponse> commentResponses = comments.stream()
                .map(commentResponse -> CommentResponse.builder()
                        .id(commentResponse.getId())
                        .content(commentResponse.getContent())
                        .author(commentResponse.getUser().getEmail())
                        .post_id(commentResponse.getPost().getPostId().toString())
                        .create_at(commentResponse.getCreateAt().toString())
                        .build()).toList();

        return CommentsResponse.builder()
                .comments(commentResponses)
                .build();
    }

    public Boolean modifyComment(AuthInfo authInfo, Integer id, CommentPutRequest commentPutRequest){
        try {
            Comment comment = commentRepository.findById(id).get();
            User user = userRepository.findById(authInfo.getUserId().intValue()).get();

            if(user != comment.getUser()){
                throw new RuntimeException("해당 유저가 아닙니다.");
            }

            comment.setContent(commentPutRequest.getContent());
            commentRepository.save(comment);

            return true;
        }catch (RuntimeException e){
            e.printStackTrace();

            return false;
        }
    }

    public Integer getCommentCountByPost(Integer postId){
        Post post = postRepository.findById(postId).get();

        return commentRepository.countByPost(post);
    }
}

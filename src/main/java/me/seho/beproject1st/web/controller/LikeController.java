package me.seho.beproject1st.web.controller;

import lombok.RequiredArgsConstructor;
import me.seho.beproject1st.domain.Likes;
import me.seho.beproject1st.service.LikeService;
import me.seho.beproject1st.web.dto.auth.AuthInfo;
import me.seho.beproject1st.web.dto.post.PostsResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;
    @GetMapping("/likes/{post_id}")
    public Integer getLikesCount(@PathVariable(value = "post_id") Integer postId){
        return likeService.getLikesCount(postId);
    }

    @GetMapping("/set-likes/{post_id}")
    public Integer setLikesCount(AuthInfo authInfo, @PathVariable(value = "post_id") Integer postId){
        return likeService.setLikesCount(authInfo, postId);
    }

    @GetMapping("/get-likes-by-user-id")
    public ResponseEntity<?> getLikesByUserId(AuthInfo authInfo){
        PostsResponse postsResponse = likeService.getLikesByUserId(authInfo.getUserId().intValue());

        if(postsResponse != null){
            return ResponseEntity.ok().body(postsResponse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("검색한 결과가 없습니다.");
        }
    }
}

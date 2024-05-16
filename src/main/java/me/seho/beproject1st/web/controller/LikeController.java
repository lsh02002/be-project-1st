package me.seho.beproject1st.web.controller;

import lombok.RequiredArgsConstructor;
import me.seho.beproject1st.service.LikeService;
import me.seho.beproject1st.web.dto.auth.AuthInfo;
import me.seho.beproject1st.web.dto.like.LikeRequest;
import org.springframework.web.bind.annotation.*;

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
}

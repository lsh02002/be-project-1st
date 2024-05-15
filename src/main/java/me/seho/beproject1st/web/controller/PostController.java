package me.seho.beproject1st.web.controller;

import lombok.RequiredArgsConstructor;
import me.seho.beproject1st.domain.User;
import me.seho.beproject1st.service.PostService;
import me.seho.beproject1st.web.dto.ResultResponse;
import me.seho.beproject1st.web.dto.auth.AuthInfo;
import me.seho.beproject1st.web.dto.post.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    @PostMapping("/posts")
    public ResponseEntity<ResultResponse> registerPost(AuthInfo authInfo, @RequestBody PostRequest postRequest){

        Boolean result = postService.registerPost(authInfo, postRequest);

        ResultResponse resultResponse;

        if(result){
            resultResponse = ResultResponse.builder()
                    .message("글 등록이 완료되었습니다.")
                    .build();

            return ResponseEntity.ok().body(resultResponse);
        }

        resultResponse = ResultResponse.builder()
                .message("글 등록이 잘 되지 않았습니다.")
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultResponse);
    }

    @GetMapping("/posts")
    public ResponseEntity<?> getPosts(){
        PostsResponse postsResponse = postService.getAllPost();

        if(postsResponse != null){
            return ResponseEntity.ok().body(postsResponse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("검색한 결과가 없습니다.");
        }
    }

    @GetMapping("/posts/search")
    public ResponseEntity<?> getPostsByUserEmail(@RequestParam("author_email") String authorEmail){
        PostsResponse postsResponse = postService.getPostsByUserEmail(authorEmail);

        if(postsResponse != null){
            return ResponseEntity.ok().body(postsResponse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("검색한 결과가 없습니다.");
        }
    }

    @PutMapping("/posts/{post_id}")
    public PostPutResponse modifyPost(AuthInfo authInfo, @PathVariable(value = "post_id") Integer postId, @RequestBody PostPutRequest postPutRequest){
        return postService.modifyPost(authInfo, postId, postPutRequest);
    }
}

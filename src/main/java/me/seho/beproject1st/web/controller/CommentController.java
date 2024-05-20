package me.seho.beproject1st.web.controller;

import lombok.AllArgsConstructor;
import me.seho.beproject1st.service.CommentService;
import me.seho.beproject1st.web.dto.ResultResponse;
import me.seho.beproject1st.web.dto.auth.AuthInfo;
import me.seho.beproject1st.web.dto.comment.CommentPutRequest;
import me.seho.beproject1st.web.dto.comment.CommentRequest;
import me.seho.beproject1st.web.dto.comment.CommentsResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor

public class CommentController {
    private final CommentService commentService;

    @PostMapping("/comments")
    public ResponseEntity<?> registerComment(AuthInfo authInfo, @RequestBody CommentRequest commentRequest){
        Boolean result = commentService.registerComment(authInfo, commentRequest);

        ResultResponse resultResponse;

        if(result){
            resultResponse = ResultResponse.builder()
                    .message("댓글이 성공적으로 작성되었습니다.")
                    .build();

            return ResponseEntity.ok().body(resultResponse);
        }

        resultResponse = ResultResponse.builder()
                .message("댓글 작성이 잘 되지 않았습니다.")
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultResponse);
    }

    @GetMapping("/comments")
    public ResponseEntity<?> getComments(){
        CommentsResponse commentsResponse = commentService.getComments();

        if(commentsResponse != null){
            return ResponseEntity.ok().body(commentsResponse);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("댓글이 없습니다");
    }

    @GetMapping("/comments-by-user-email/{user_email}")
    public ResponseEntity<?> getCommentsByUserEmail(@PathVariable(value = "user_email") String userEmail){
        CommentsResponse commentsResponse = commentService.getCommentsByUserEmail(userEmail);

        if(commentsResponse != null){
            return ResponseEntity.ok().body(commentsResponse);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("댓글이 없습니다");
    }

    @PutMapping("/comments/{comment_id}")
    public ResponseEntity<?> modifyComment(AuthInfo authInfo, @PathVariable(value = "comment_id") Integer id, @RequestBody CommentPutRequest commentPutRequest){

        Boolean result = commentService.modifyComment(authInfo, id, commentPutRequest);

        ResultResponse resultResponse;

        if(result){
            resultResponse = ResultResponse.builder()
                    .message("댓글이 성공적으로 수정되었습니다.")
                    .build();

            return ResponseEntity.ok().body(resultResponse);
        }

        resultResponse = ResultResponse.builder()
                .message("댓글 수정이 잘 되지 않았습니다.")
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultResponse);
    }
}

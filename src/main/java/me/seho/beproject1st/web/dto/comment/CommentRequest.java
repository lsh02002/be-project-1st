package me.seho.beproject1st.web.dto.comment;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class CommentRequest {
    private String content;
    private String author;
    private String post_id;
}

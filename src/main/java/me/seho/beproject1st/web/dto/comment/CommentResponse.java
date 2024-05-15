package me.seho.beproject1st.web.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class CommentResponse {
    private Integer id;
    private String content;
    private String author;
    private String post_id;
    private String create_at;
}

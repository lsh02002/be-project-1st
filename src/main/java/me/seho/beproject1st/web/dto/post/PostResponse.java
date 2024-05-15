package me.seho.beproject1st.web.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class PostResponse {
    private Integer id;
    private String title;
    private String content;
    private String author;
    private String create_at;
    private String modify_at;
    private Integer count;
}

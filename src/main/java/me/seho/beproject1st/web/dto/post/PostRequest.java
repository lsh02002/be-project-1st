package me.seho.beproject1st.web.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class PostRequest {
    private String title;
    private String author;
    private String content;
}

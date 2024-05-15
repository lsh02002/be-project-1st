package me.seho.beproject1st.web.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class PostsResponse {
    private List<PostResponse> posts;
}

package me.seho.beproject1st.web.dto.comment;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class CommentsResponse {
    List<CommentResponse> comments;
}

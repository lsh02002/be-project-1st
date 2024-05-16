package me.seho.beproject1st.web.dto.like;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class LikeRequest {
    private String email;
    private Integer post_id;
}

package me.seho.beproject1st.domain;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Builder
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Integer postId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String title;

    private String content;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "modify_at")
    private LocalDateTime modifyAt;
}

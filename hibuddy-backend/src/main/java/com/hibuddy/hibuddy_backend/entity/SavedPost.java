package com.hibuddy.hibuddy_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "saved_posts",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "post_id"}),
        indexes = {
                @Index(name = "idx_saved_user", columnList = "user_id"),
                @Index(name = "idx_saved_post", columnList = "post_id")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"user", "post"})
public class SavedPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    @JsonIgnore
    private Post post;

    private LocalDateTime savedAt = LocalDateTime.now();
}

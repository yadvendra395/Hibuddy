package com.hibuddy.hibuddy_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "follows",
        uniqueConstraints = @UniqueConstraint(columnNames = {"follower_id", "following_id"}),
        indexes = {
                @Index(name = "idx_follower", columnList = "follower_id"),
                @Index(name = "idx_following", columnList = "following_id")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"follower", "following"})
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long followId;

    // who follows
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id", nullable = false)
    private User follower;

    // who is being followed
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "following_id", nullable = false)
    private User following;
    @Column(nullable = false, updatable = false)
    private LocalDateTime followedAt;

    @PrePersist
    protected void onFollow() {
        followedAt = LocalDateTime.now();
    }
}
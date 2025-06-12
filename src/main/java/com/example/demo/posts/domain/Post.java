package com.example.demo.posts.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 200)
    private String content;

    @Column(nullable = false, length = 50) // 👈 작성자
    private String author; // ← username

    private LocalDateTime createdAt = LocalDateTime.now();

    @LastModifiedDate // ★ 추가
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // 👉 JPA 기본 생성자
    protected Post() {
    }

    // 👉 편의 생성자
    public Post(String content, String author) {
        this.content = content;
        this.author = author;
    }

    // --- Getter ---
    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() { // ★ 추가
        return updatedAt;
    }

    public void setContent(String content) {
        if (content == null || content.trim().isEmpty())
            throw new IllegalArgumentException("내용이 비어 있습니다.");
        this.content = content.trim();
    }
}

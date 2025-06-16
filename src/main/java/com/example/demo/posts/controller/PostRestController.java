package com.example.demo.posts.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.posts.domain.Post;
import com.example.demo.posts.repository.PostRepository;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/board")
public class PostRestController {

    private final PostRepository postRepo;

    @PutMapping("/{id}")
    @PreAuthorize("@postService.isOwner(#id, authentication) or hasRole('ADMIN')")
    @Transactional("postsTransactionManager") // ★ posts 전용 TX
    public ResponseEntity<?> update(@PathVariable Long id,
            @RequestBody Map<String, String> payload) {

        Post post = postRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("invalid id"));
        post.setContent(payload.get("content")); // Dirty-Checking
        // save() 호출 생략

        return ResponseEntity.ok(Map.of(
                "id", post.getId(),
                "content", post.getContent()));
    }
}
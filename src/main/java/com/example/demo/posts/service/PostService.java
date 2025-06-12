package com.example.demo.posts.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.demo.posts.repository.PostRepository;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepo;

    /** 글 작성자가 현재 로그인 사용자와 같은지 */
    public boolean isOwner(Long postId, Authentication auth) {
        return postRepo.findById(postId)
                       .map(p -> p.getAuthor().equals(auth.getName()))
                       .orElse(false);
    }
}

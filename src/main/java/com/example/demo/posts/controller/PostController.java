package com.example.demo.posts.controller;

import lombok.RequiredArgsConstructor; // ← Lombok
import org.springframework.stereotype.Controller; // ← Spring MVC
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Sort; // ← JPA Sort
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;

import java.util.List;

import com.example.demo.posts.domain.Post;
import com.example.demo.posts.repository.PostRepository;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor // ← Lombok: 생성자 주입
public class PostController {

    private final PostRepository postRepo;

    /** 목록 + 입력 화면 */
    @GetMapping
    public String board(Model model) {
        List<Post> posts = postRepo.findAll(Sort.by(Sort.Direction.DESC, "id"));
        model.addAttribute("posts", posts);
        return "board";
    }

    /** 한 줄 글 등록 */
    @PostMapping
    public String write(@RequestParam String content,
            Authentication auth) { // ← 현재 로그인 정보

        String username = auth.getName(); // 로그인 아이디
        postRepo.save(new Post(content, username));
        return "redirect:/board";
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("@postService.isOwner(#id, authentication) or hasRole('ADMIN')")
    public String delete(@PathVariable Long id) {
        postRepo.deleteById(id); // 권한 이미 통과됨
        return "redirect:/board";
    }
}
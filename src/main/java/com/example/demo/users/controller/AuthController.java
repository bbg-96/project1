package com.example.demo.users.controller;

import com.example.demo.users.dto.SignupRequest;
import com.example.demo.users.service.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;                // ← 추가
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final UserService userService;          // ← 추가

    // 생성자 주입
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signupForm(@ModelAttribute("req") SignupRequest req) {
        return "signup";
    }

    @PostMapping("/signup")
    public String signupSubmit(@ModelAttribute("req") SignupRequest req,
                               Model model) {
        try {
            userService.register(req);
        } catch (IllegalArgumentException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "signup";
        }
        return "redirect:/login";
    }
}

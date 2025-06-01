package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.dto.SignupRequest;
import com.example.demo.repository.UserRepository;
import org.slf4j.Logger;                           // ← 추가
import org.slf4j.LoggerFactory;                    // ← 추가
import org.springframework.security.crypto.password.PasswordEncoder;  // ← 추가
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;       // ← 추가

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Logger log = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(SignupRequest req) {
        log.info("회원가입 시도: {}", req.getUsername());
        userRepository.findByUsername(req.getUsername())
            .ifPresent(u -> {
                throw new IllegalArgumentException("이미 존재하는 사용자명입니다.");
            });

        String encodedPw = passwordEncoder.encode(req.getPassword());
        User user = new User(req.getUsername(), encodedPw, "USER");
        User saved = userRepository.save(user);
        log.info("저장 완료: id={} username={}", saved.getId(), saved.getUsername());
    }
}

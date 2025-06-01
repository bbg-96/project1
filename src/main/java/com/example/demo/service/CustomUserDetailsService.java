package com.example.demo.service;

import com.example.demo.domain.User;                         // 도메인 User
import com.example.demo.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepo;

    // ① 생성자 주입 추가
    public CustomUserDetailsService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        // ② 도메인 User 객체 받아오기
        User userEntity = userRepo.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException(username));

        // ③ Spring Security의 UserBuilder 호출
        //     org.springframework.security.core.userdetails.User
        return org.springframework.security.core.userdetails.User
            .withUsername(userEntity.getUsername())
            .password(userEntity.getPassword())
            .roles(userEntity.getRole())    // getRole()이 "USER" 같은 값 반환한다고 가정
            .build();
    }
}

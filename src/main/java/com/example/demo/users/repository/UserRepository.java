package com.example.demo.users.repository;   // ← 이 패키지 선언을 꼭 확인

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.users.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}

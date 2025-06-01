package com.example.demo.repository;   // ← 이 패키지 선언을 꼭 확인

import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}

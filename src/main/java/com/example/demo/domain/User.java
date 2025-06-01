// src/main/java/com/example/demo/domain/User.java
package com.example.demo.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "users")  // 테이블명이 USERS 여야 한다면
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    // (추가 필드가 필요하면 여기에 선언)
    @Column(nullable = false)
    private String role;

    // 기본 생성자
    public User() {}

    // ↓ 3개 인자 생성자 추가
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // ↓ 기존 2개 인자 생성자(있었다면 유지)
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getter/Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
}

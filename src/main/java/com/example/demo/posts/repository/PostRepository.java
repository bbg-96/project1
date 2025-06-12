package com.example.demo.posts.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.posts.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> { }

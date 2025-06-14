package com.example.demo.config;   // 프로젝트 패키지에 맞게 바꿔주세요

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ActuatorSecurityConfig {

    // ① Actuator 전용 체인 — 반드시 먼저 실행되도록 @Order(1)
    @Bean
    @Order(1)
    SecurityFilterChain actuatorChain(HttpSecurity http) throws Exception {
        http
          .securityMatcher(EndpointRequest.to("prometheus"))   // 또는 toAnyEndpoint()
          .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
          .csrf(csrf -> csrf.disable());
        return http.build();
    }

    // ② 애플리케이션 나머지 체인
    @Bean
    SecurityFilterChain appChain(HttpSecurity http) throws Exception {
        http
          .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
          .formLogin(Customizer.withDefaults());
        return http.build();
    }
}

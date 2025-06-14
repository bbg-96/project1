package com.example.demo.config;   // 프로젝트 패키지에 맞게 바꿔주세요

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Order(1)  // ★ 기존 SecurityConfig(대개 기본 우선순위 100)보다 먼저
public class ActuatorSecurityConfig {

    @Bean
    public SecurityFilterChain actuatorChain(HttpSecurity http) throws Exception {

        http
            // ① Actuator ID 기준으로 health·info·prometheus 만 잡는다
            .securityMatcher(EndpointRequest.to("health", "info", "prometheus"))

            // ② 모두 허용
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())

            // ③ GET 전용이므로 CSRF 꺼도 무방 (원하면 주석 처리)
            .csrf(AbstractHttpConfigurer::disable)

            // ④ 세션(쿠키) 필요 없으면 STATELESS 로
            .sessionManagement(sm ->
                sm.sessionCreationPolicy(
                    org.springframework.security.config.http.SessionCreationPolicy.STATELESS));

        return http.build();
    }
}

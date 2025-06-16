package com.example.demo.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.Customizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
public class ActuatorSecurityConfig {

    /** 0번 체인 – Actuator 전용 */
    @Bean @Order(0)
    SecurityFilterChain actuator(HttpSecurity http) throws Exception {
        http.securityMatcher(EndpointRequest.toAnyEndpoint())
            .authorizeHttpRequests(r -> r.anyRequest().permitAll())
            .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    /** 애플리케이션 체인 */
    @Bean
    SecurityFilterChain app(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(r -> r.anyRequest().authenticated())
            .formLogin(withDefaults());   // ← 오류 해결
        return http.build();
    }
}

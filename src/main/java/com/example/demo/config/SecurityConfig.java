package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity          // @PreAuthorize 등을 쓸 때
public class SecurityConfig {

    /** ① 비밀번호 인코더 — BCrypt 하나만 Bean 으로 등록 */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /** ② 시큐리티 필터 체인 */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            /* 개발 중 CSRF 잠깐 끄기 (운영에선 켜 두기) */
            //.csrf(csrf -> csrf.disable())

            /* ②-1 접근 권한 규칙 */
            .authorizeHttpRequests(auth -> auth
                // 정적 리소스 + 로그인/회원가입 페이지만 모두 허용
                .requestMatchers(
                    "/", "/css/**", "/js/**", "/images/**",
                    "/login", "/signup", "/error"
                ).permitAll()
                .requestMatchers("/actuator/health", "/actuator/info", "/actuator/prometheus").permitAll()
                // 그 외는 인증 필요
                .anyRequest().authenticated()
            )

            /* ②-2 폼 로그인 */
            .formLogin(form -> form
                .loginPage("/login")          // GET: 로그인 화면
                .loginProcessingUrl("/login") // 로그인 처리를 하는 Url 명시
                .defaultSuccessUrl("/board", true)
                .failureUrl("/login?error")   // 실패 시
                .permitAll()
            )

            /* ②-3 로그아웃 */
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true)
            );

        return http.build();
    }
}

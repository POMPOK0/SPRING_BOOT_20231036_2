package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration // 스프링 설정 클래스 지정
@EnableWebSecurity // 스프링 보안 활성화
public class SecurityConfig { 

    @Bean // 명시적 의존성 주입: Security Filter Chain 생성
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 헤더 설정 - XSS Protection 헤더 추가
            .headers(headers -> headers
                .addHeaderWriter((request, response) -> {
                    response.setHeader("X-XSS-Protection", "1; mode=block");
                })
            )
            // CSRF 설정 (기본 활성화)
            // .csrf(withDefaults())
            .csrf(csrf -> csrf.disable())
            // 세션 관리 설정
            .sessionManagement(session -> session
                .invalidSessionUrl("/session-expired") // 세션 만료 시 이동할 URL
                .maximumSessions(1) // 사용자당 최대 세션 수
                .maxSessionsPreventsLogin(true) // 최대 세션 초과 시 새로운 로그인 방지
            );

        return http.build(); // 보안 설정 반환
    }

    @Bean // 비밀번호 암호화 설정
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // BCrypt 알고리즘으로 비밀번호 암호화
    }
}

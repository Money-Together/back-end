package com.example.moneytogether1.config;

import com.example.moneytogether1.jwt.JwtFilter;
import com.example.moneytogether1.jwt.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SecurityConfig {

    private final JwtUtil jwtUtil;

    public SecurityConfig(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())        // CSRF 비활성화
                .formLogin(form -> form.disable())   // 기본 폼 로그인 비활성화
                .httpBasic(basic -> basic.disable()) // HTTP Basic 인증 비활성화
                .addFilterBefore(new JwtFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class) // JWT 필터 추가
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",
                                "/auth/google",
                                "/auth/refresh",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs",
                                "/v3/api-docs/**",
                                "/swagger-resources/**",
                                "/webjars/**"
                        ).permitAll()
                        .anyRequest().authenticated())     // 나머지 요청은 인증 필요
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // 세션 사용하지 않음(Stateless)

        return http.build();
    }
}

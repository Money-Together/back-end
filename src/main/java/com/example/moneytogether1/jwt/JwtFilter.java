package com.example.moneytogether1.jwt;

import com.example.moneytogether1.dto.MemberDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;


@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    // 특정 경로는 JWT 검사 건너뛰기
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/auth/google")
                || path.startsWith("/auth/refresh")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs")
                || path.startsWith("/swagger-resources");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            log.info("Authorization token is null or invalid");
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.substring(7);
        if (jwtUtil.isExpired(token)) {
            log.info("Token is expired");
            filterChain.doFilter(request, response);
            return;
        }

        // 토큰에서 uid, memberId 가져오기
        String uid = jwtUtil.getUidFromToken(token);
        Long memberId = jwtUtil.getMemberIdFromToken(token);

        // 인증 객체 생성
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(memberId, null, List.of());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}


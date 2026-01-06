package com.shopit.shopit.global.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        // 1. Authorization 헤더 추출
        String authorizationHeader = request.getHeader("Authorization");

        // 2. 토큰이 없으면 그냥 다음 필터로
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Bearer 제거
        String accessToken = authorizationHeader.substring(7);

        // 4. 토큰 유효성 검사
        if (jwtTokenProvider.validateToken(accessToken)) {

            // 5. 인증 객체 생성 후 SecurityContext 저장
            SecurityContextHolder.getContext().setAuthentication(
                    jwtTokenProvider.getAuthentication(accessToken)
            );
        }

        // 6. 다음 필터로
        filterChain.doFilter(request, response);
    }
}

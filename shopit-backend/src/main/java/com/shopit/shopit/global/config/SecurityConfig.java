package com.shopit.shopit.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 가장 흔하게 쓰이는 암호화 방식
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CSRF 비활성화 (REST API용)
                .csrf(csrf -> csrf.disable())

                // 세션 없이 stateless
                .sessionManagement(session -> session
                        .sessionCreationPolicy(org.springframework.security.config.http.SessionCreationPolicy.STATELESS)
                )

                // 로그인 페이지 비활성화 / 모든 요청 허용 예시
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/**").permitAll() // 인증 없이 API 접근 허용
                        .anyRequest().authenticated()              // 그 외 요청은 인증 필요
                )
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin) // H2 콘솔사용
                )

                // 기본 로그인 폼 비활성화
                .formLogin(form -> form.disable())

                // HTTP Basic 사용 (선택)
                .httpBasic(httpBasic -> httpBasic.disable());

        return http.build();
    }
}

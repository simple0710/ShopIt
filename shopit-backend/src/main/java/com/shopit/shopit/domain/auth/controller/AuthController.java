package com.shopit.shopit.domain.auth.controller;

import com.shopit.shopit.domain.auth.dto.request.LoginRequest;
import com.shopit.shopit.domain.auth.dto.request.RefreshTokenRequest;
import com.shopit.shopit.domain.auth.dto.response.AccessTokenResponse;
import com.shopit.shopit.domain.auth.dto.response.LoginResponse;
import com.shopit.shopit.domain.auth.service.AuthService;
import com.shopit.shopit.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RedisTemplate<String, String> redisTemplate;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(
                ApiResponse.success(response)
        );
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse<AccessTokenResponse>> refreshToken(@RequestBody RefreshTokenRequest request) {
        String newAccessToken = authService.refreshAccessToken(request.getUserId(), request.getRefreshToken());
        return ResponseEntity.ok(
                ApiResponse.success(new AccessTokenResponse(newAccessToken))
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long userId = (Long) auth.getPrincipal();

        authService.logout(userId);

        return ResponseEntity.ok(ApiResponse.success(null));
    }
}

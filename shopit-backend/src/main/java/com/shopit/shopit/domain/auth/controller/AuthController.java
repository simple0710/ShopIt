package com.shopit.shopit.domain.auth.controller;

import com.shopit.shopit.domain.auth.dto.request.LoginRequest;
import com.shopit.shopit.domain.auth.dto.request.RefreshTokenRequest;
import com.shopit.shopit.domain.auth.dto.response.AccessTokenResponse;
import com.shopit.shopit.domain.auth.dto.response.LoginResponse;
import com.shopit.shopit.domain.auth.service.AuthService;
import com.shopit.shopit.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

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
}

package com.shopit.shopit.domain.auth.service;

import com.shopit.shopit.domain.auth.dto.response.LoginResponse;
import com.shopit.shopit.domain.auth.exception.InvalidCredentialsException;
import com.shopit.shopit.domain.auth.exception.InvalidRefreshTokenException;
import com.shopit.shopit.domain.user.entity.User;
import com.shopit.shopit.domain.user.exception.UserNotFoundException;
import com.shopit.shopit.domain.user.repository.UserRepository;
import com.shopit.shopit.global.config.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> redisTemplate;

    public LoginResponse login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        String accessToken = jwtTokenProvider.generateAccessToken(user);
        String refreshToken = jwtTokenProvider.generateRefreshToken(user);

        String key = "refreshToken:" + user.getId();
        redisTemplate.opsForValue().set(key, refreshToken, jwtTokenProvider.getRefreshTokenValidity(), TimeUnit.MICROSECONDS);

        return new LoginResponse(accessToken, refreshToken);
    }

    // RefreshToken으로 AccessToken 재발급
    public String refreshAccessToken(Long userId, String refreshToken) {
        String key = "refreshToken:" + userId;
        String storedToken = redisTemplate.opsForValue().get(key);

        if (storedToken == null || !storedToken.equals(refreshToken)) {
            throw new InvalidRefreshTokenException();
        }

        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        return jwtTokenProvider.generateAccessToken(user);
    }
}

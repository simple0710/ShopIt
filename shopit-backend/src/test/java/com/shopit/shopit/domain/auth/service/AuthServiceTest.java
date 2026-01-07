package com.shopit.shopit.domain.auth.service;

import com.shopit.shopit.domain.auth.dto.response.LoginResponse;
import com.shopit.shopit.domain.auth.exception.InvalidCredentialsException;
import com.shopit.shopit.domain.user.entity.User;
import com.shopit.shopit.domain.user.repository.UserRepository;
import com.shopit.shopit.global.config.security.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private RedisTemplate<String, String> redisTemplate;

    @Mock
    private ValueOperations<String, String> valueOperations;

    // login
    @Test
    public void 로그인_성공() {
        // given
        String email = "test@test.com";
        String rawPassword = "password123";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        String name = "test";
        String accessToken = "accessToken";
        String refreshToken = "refreshToken";

        User user = User.create(email, encodedPassword, name);

        given(userRepository.findByEmail(email)).willReturn(Optional.of(user));
        given(passwordEncoder.matches(rawPassword, encodedPassword)).willReturn(true);
        given(jwtTokenProvider.generateAccessToken(user)).willReturn(accessToken);
        given(jwtTokenProvider.generateRefreshToken(user)).willReturn(refreshToken);
        given(redisTemplate.opsForValue()).willReturn(valueOperations);

        // when
        LoginResponse response = authService.login(email, rawPassword);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getAccessToken()).isEqualTo(accessToken);
        assertThat(response.getRefreshToken()).isEqualTo(refreshToken);

        verify(valueOperations).set(
                startsWith("refreshToken:"),
                eq(refreshToken),
                anyLong(),
                eq(TimeUnit.MICROSECONDS)
        );
    }

    @Test
    public void 로그인_실패_잘못된_자격증명() {
        // given
        String email = "test@test.com";
        String rawPassword = "password123";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        String name = "test";

        User user = User.create(email, encodedPassword, name);

        given(userRepository.findByEmail(email)).willReturn(Optional.of(user));
        given(passwordEncoder.matches(rawPassword, encodedPassword)).willReturn(false);

        // when & then
        assertThatThrownBy(() -> authService.login(email, rawPassword))
                .isInstanceOf(InvalidCredentialsException.class);
    }

    // logout
    @Test
    public void 로그아웃_성공() {
        // given
        Long userId = 1L;
        String key = "refreshToken:"+userId;

        // when
        authService.logout(userId);

        // then
        verify(redisTemplate).delete(key);
    }
}

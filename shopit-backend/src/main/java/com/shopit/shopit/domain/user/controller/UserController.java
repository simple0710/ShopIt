package com.shopit.shopit.domain.user.controller;

import com.shopit.shopit.domain.user.dto.request.UserRegisterRequest;
import com.shopit.shopit.domain.user.dto.response.UserProfileResponse;
import com.shopit.shopit.domain.user.dto.response.UserRegisterResponse;
import com.shopit.shopit.domain.user.entity.User;
import com.shopit.shopit.domain.user.service.UserService;
import com.shopit.shopit.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 회원가입 API
     * - 요청: UserRegisterRequest(JSON)
     * - 응답: ApiResponse<UserRegisterResponse>
     * - POST 요청으로 새로운 사용자 생성
     */
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<UserRegisterResponse>> register(@RequestBody UserRegisterRequest request) {
        Long userId = userService.register(
                request.getEmail(),
                request.getPassword(),
                request.getName()
        );

        // ApiResponse.success 사용
        // - HTTP 200
        // - data: 생성된 userId 반환
        return ResponseEntity.ok(
                ApiResponse.success(new UserRegisterResponse(userId))
        );
    }

    /**
     * 회원 정보 조회 API
     * - 요청: PathVariable userId
     * - 응답: ApiResponse<UserResponse>
     * - GET 요청으로 사용자 정보 조회
     */
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserProfileResponse>> getUser(@PathVariable Long userId) {
        User user = userService.getUser(userId);
        return ResponseEntity.ok(
                ApiResponse.success(UserProfileResponse.from(user))
        );
    }
}

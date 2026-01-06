package com.shopit.shopit.domain.auth.exception;

import com.shopit.shopit.global.common.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AuthErrorCode implements ErrorCode {

    INVALID_CREDENTIALS(4001, "Invalid credentials", 401),
    EXPIRED_TOKEN(4002, "토큰이 만료되었습니다", 401),
    INVALID_REFRESH_TOKEN(4003, "Invalid RefreshToken", 401),
    USER_NOT_FOUND(4004, "사용자를 찾을 수 없습니다", 404);

    private final int code;
    private final String message;
    private final int httpStatus;
}

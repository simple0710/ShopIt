package com.shopit.shopit.domain.user.exception;

import com.shopit.shopit.global.common.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserErrorCode implements ErrorCode {
    USER_NOT_FOUND(3000, "User not found", 404),
    DUPLICATE_EMAIL(3001, "Email already exists", 409),
    INVALID_USER_STATUS(3002, "Invalid user status", 403),
    USER_ACCESS_DENIED(3003, "User access denied", 403),
    PASSWORD_MISMATCH(3004, "Password mismatch", 403);

    private final int code;
    private final String message;
    private final int httpStatus;
}

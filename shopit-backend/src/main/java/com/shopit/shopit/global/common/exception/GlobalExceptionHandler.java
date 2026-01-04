package com.shopit.shopit.global.common.exception;

import com.shopit.shopit.domain.user.exception.DuplicateEmailException;
import com.shopit.shopit.global.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ApiResponse<Object>> handleDuplicateEmail(ServiceException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ApiResponse.fail(
                        ex.getErrorCode().getMessage(),
                        ex.getErrorCode().getCode(),
                        ex.getErrorCode().getHttpStatus()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleAllException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.fail(
                        "Internal Server Error",
                        5000,
                        HttpStatus.INTERNAL_SERVER_ERROR.value() // 500
                ));
    }
}
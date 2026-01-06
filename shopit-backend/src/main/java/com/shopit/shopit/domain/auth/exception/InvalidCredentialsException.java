package com.shopit.shopit.domain.auth.exception;

import com.shopit.shopit.global.common.exception.ServiceException;

public class InvalidCredentialsException extends ServiceException {

    public InvalidCredentialsException() {
        super(AuthErrorCode.INVALID_CREDENTIALS);
    }
}

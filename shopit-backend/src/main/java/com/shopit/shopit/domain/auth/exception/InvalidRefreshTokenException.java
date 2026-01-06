package com.shopit.shopit.domain.auth.exception;

import com.shopit.shopit.global.common.exception.ServiceException;

public class InvalidRefreshTokenException extends ServiceException {

    public InvalidRefreshTokenException() {
        super(AuthErrorCode.INVALID_REFRESH_TOKEN);
    }
}

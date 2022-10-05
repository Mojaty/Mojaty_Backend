package com.motivation.mojaty.global.exception.jwt;

import com.motivation.mojaty.global.exception.application.CustomException;
import com.motivation.mojaty.global.exception.application.ErrorCode;

public class ExpiredTokenException extends CustomException {
    public final static ExpiredTokenException EXCEPTION = new ExpiredTokenException();

    private ExpiredTokenException() {
        super(ErrorCode.EXPIRED_TOKEN);
    }
}

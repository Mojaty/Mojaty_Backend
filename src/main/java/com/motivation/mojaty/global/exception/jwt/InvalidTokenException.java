package com.motivation.mojaty.global.exception.jwt;

import com.motivation.mojaty.global.exception.application.CustomException;
import com.motivation.mojaty.global.exception.application.ErrorCode;

public class InvalidTokenException extends CustomException {

    public final static InvalidTokenException EXCEPTION = new InvalidTokenException();

    private InvalidTokenException() {
        super(ErrorCode.INVALID_TOKEN);
    }

}

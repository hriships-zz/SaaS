package com.appdirect.common.exceptions;

/**
 * Created by hrishikeshshinde on 23/11/16.
 * Authentication related exceptions
 */
public class AuthException extends RuntimeException {

    public static final String UNAUTHORIZED = "401 Unauthorized";

    public AuthException() {
    }

    public AuthException(String message) {
        super(message);
    }

    public AuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthException(Throwable cause) {
        super(cause);
    }

    public AuthException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

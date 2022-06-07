package com.company.project.exception;

/**
 * 认证失败异常
 *
 * @author DanielQSL
 */
public class AuthenticationFailException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AuthenticationFailException() {
        super();
    }

    public AuthenticationFailException(String message) {
        super(message);
    }

    public AuthenticationFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthenticationFailException(Throwable cause) {
        super(cause);
    }

}

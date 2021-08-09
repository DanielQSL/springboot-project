package com.company.project.exception;

/**
 * 普通自定义异常
 *
 * @author DanielQSL
 */
public class HbaseException extends RuntimeException {

    public HbaseException() {
    }

    public HbaseException(String message) {
        super(message);
    }

    public HbaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public HbaseException(Throwable cause) {
        super(cause);
    }

}

package com.company.project.exception;

/**
 * 普通自定义异常
 *
 * @author DanielQSL
 */
public class HbaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public HbaseException() {
        super();
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

package com.qa.common_libs.exception;

public class ResourceNotFoundException extends AbstractUserErrorExceptionBase {

    public ResourceNotFoundException() {
        super();
    }

    public ResourceNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public ResourceNotFoundException(String msg) {
        super(msg);
    }

    public ResourceNotFoundException(String key, String msg) {
        super(msg);
    }

    public ResourceNotFoundException(String key, String msg, Throwable cause) {
        super(msg, cause);
    }

}

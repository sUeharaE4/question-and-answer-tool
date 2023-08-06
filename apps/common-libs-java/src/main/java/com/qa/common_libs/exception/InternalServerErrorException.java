package com.qa.common_libs.exception;

public class InternalServerErrorException extends AbstractAppExceptionBase {

    public InternalServerErrorException() {
        super();
    }

    public InternalServerErrorException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public InternalServerErrorException(String msg) {
        super(msg);
    }

    public InternalServerErrorException(String key, String msg) {
        super(msg);
    }

    public InternalServerErrorException(String key, String msg, Throwable cause) {
        super(msg, cause);
    }

}

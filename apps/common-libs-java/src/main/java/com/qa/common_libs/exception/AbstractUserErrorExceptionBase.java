package com.qa.common_libs.exception;

public abstract class AbstractUserErrorExceptionBase extends AbstractAppExceptionBase {

    protected AbstractUserErrorExceptionBase() {
        super();
    }

    protected AbstractUserErrorExceptionBase(String msg, Throwable cause) {
        super(msg, cause);
    }

    protected AbstractUserErrorExceptionBase(String msg) {
        super(msg);
    }

    protected AbstractUserErrorExceptionBase(String key, String msg) {
        super(key, msg);
    }

    protected AbstractUserErrorExceptionBase(String key, String msg, Throwable cause) {
        super(msg, cause);
    }

}

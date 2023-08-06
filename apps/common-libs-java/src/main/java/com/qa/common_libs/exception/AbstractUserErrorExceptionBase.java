package com.qa.common_libs.exception;

public abstract class AbstractUserErrorExceptionBase extends AbstractAppExceptionBase {

    public AbstractUserErrorExceptionBase() {
        super();
    }

    public AbstractUserErrorExceptionBase(String msg, Throwable cause) {
        super(msg, cause);
    }

    public AbstractUserErrorExceptionBase(String msg) {
        super(msg);
    }

    public AbstractUserErrorExceptionBase(String key, String msg) {
        super(key, msg);
    }

    public AbstractUserErrorExceptionBase(String key, String msg, Throwable cause) {
        super(msg, cause);
    }

}

package com.qa.common_libs.exception;

public abstract class AbstractAppExceptionBase extends RuntimeException {

    private String errKey = "can not specify the error key.";
    private String errMsg = "can not specify the error message.";

    protected AbstractAppExceptionBase() {
        super();
    }

    protected AbstractAppExceptionBase(String msg, Throwable cause) {
        super(msg, cause);
        this.errMsg = msg;
    }

    protected AbstractAppExceptionBase(String msg) {
        super(msg);
        this.errMsg = msg;
    }

    protected AbstractAppExceptionBase(String key, String msg) {
        super(msg);
        this.errKey = key;
        this.errMsg = msg;
    }

    protected AbstractAppExceptionBase(String key, String msg, Throwable cause) {
        super(msg, cause);
        this.errKey = key;
        this.errMsg = msg;
    }

    public ErrorData makeErrorData() {
        ErrorData errorData = new ErrorData(errKey, errMsg);
        return errorData;
    }

}

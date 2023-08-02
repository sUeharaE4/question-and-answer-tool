package com.qa.common_libs.exception;

public abstract class AbstractAppExceptionBase extends Exception {

    private String errKey = "can not specify the error key.";
    private String errMsg = "can not specify the error message.";

    public AbstractAppExceptionBase() {
        super();
    }

    public AbstractAppExceptionBase(String msg, Throwable cause) {
        super(msg, cause);
        this.errMsg = msg;
    }

    public AbstractAppExceptionBase(String msg) {
        super(msg);
        this.errMsg = msg;
    }

    public AbstractAppExceptionBase(String key, String msg) {
        super(msg);
        this.errKey = key;
        this.errMsg = msg;
    }

    public AbstractAppExceptionBase(String key, String msg, Throwable cause) {
        super(msg, cause);
        this.errKey = key;
        this.errMsg = msg;
    }

    public ErrorData makeErrorData() {
        ErrorData errorData = new ErrorData();
        errorData.setErrKey(errKey);
        errorData.setErrMsg(errMsg);
        return errorData;
    }

}

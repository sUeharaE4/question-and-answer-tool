package com.qa.common_libs.exception;

import lombok.Data;
import java.util.Objects;

@Data
public class ErrorData {

    private String errKey;
    private String errMsg;

    public String joinMessage() {
        String key = Objects.nonNull(errKey) ? errKey : "no_key";
        return String.format("%s: %s", key, errMsg);
    }
}

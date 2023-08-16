package com.qa.common_libs.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Objects;

@Data
@AllArgsConstructor
public class ErrorData {

    private final String errKey;
    private final String errMsg;

    public String joinMessage() {
        String key = Objects.nonNull(errKey) ? errKey : "no_key";
        return String.format("%s: %s", key, errMsg);
    }
}

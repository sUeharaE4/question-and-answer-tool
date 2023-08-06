package com.qa.common_libs.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorData {

    private String errKey;
    private String errMsg;

    public String joinMessage() {
        String key = Objects.nonNull(errKey) ? errKey : "no_key";
        return String.format("%s: %s", key, errMsg);
    }
}
